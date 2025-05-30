package com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache;

import com.perfums.transactions.domain.dto.OrderProductDTO;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.FragranceSize;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.FragranceSizeId;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.List;

@ApplicationScoped
public class FragranceSizePanacheRepository implements PanacheRepositoryBase<FragranceSize, FragranceSizeId> {

    public Uni<Boolean> verifyAndReserveStock(List<OrderProductDTO> products) {
        return getSession().flatMap(session ->
                processProductListSequentially(session, products, 0)
        );
    }

    private Uni<Boolean> processProductListSequentially(Mutiny.Session session, List<OrderProductDTO> products, int index) {
        if (index >= products.size()) {
            return Uni.createFrom().item(true); // todos procesados correctamente
        }

        OrderProductDTO product = products.get(index);
        FragranceSizeId id = new FragranceSizeId(product.getSizeId(), product.getFragranceId());

        return session
                .createQuery("SELECT fs FROM FragranceSize fs WHERE fs.id = :id", FragranceSize.class)
                .setParameter("id", id)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getSingleResult()
                .onItem().transformToUni(fs -> {
                    if (fs.getStock() < product.getQuantity()) {
                        return Uni.createFrom().failure(new IllegalStateException("Stock insuficiente"));
                    }

                    fs.setStock(fs.getStock() - product.getQuantity());
                    return session.merge(fs)
                            .replaceWith(true); // continuar con el siguiente producto
                })
                .flatMap(result -> processProductListSequentially(session, products, index + 1));
    }

}
