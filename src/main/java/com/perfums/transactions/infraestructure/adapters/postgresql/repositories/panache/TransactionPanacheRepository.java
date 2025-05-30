package com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache;

import com.perfums.transactions.domain.dto.OrderProductDTO;
import com.perfums.transactions.domain.dto.OrderRequestDTO;
import com.perfums.transactions.domain.dto.TransactionFragranceDTO;
import com.perfums.transactions.domain.models.StateType;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Client;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Fragrance;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.FragranceSize;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.FragranceSizeId;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.PaymentMethod;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Size;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Transaction;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.TransactionFragrance;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.TransactionFragrancePK;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TransactionPanacheRepository implements PanacheRepositoryBase<Transaction, Long> {

    public Uni<Transaction> createTransaction(
            Client client,
            List<OrderProductDTO> products,
            OrderRequestDTO requestDTO,
            PaymentMethod paymentMethod,
            String code,
            BigDecimal totalPayment
    ) {
        return getSession().flatMap(session -> {
            Transaction transaction = new Transaction();
            transaction.setClient(client);
            transaction.setPaymentMethod(paymentMethod);
            transaction.setState(StateType.WAITING_PAYMENTS);
            transaction.setEmail(requestDTO.getEmail());
            transaction.setAddress(requestDTO.getAddress());
            transaction.setCode(code);
            transaction.setTotalPayment(totalPayment);

            return session.persist(transaction)
                    .flatMap(v -> {
                        List<Uni<Void>> insertions = new ArrayList<>();

                        for (OrderProductDTO product : products) {
                            Uni<Void> insertion = session.find(Fragrance.class, product.getFragranceId())
                                    .flatMap(fragrance -> session.find(Size.class, product.getSizeId())
                                            .flatMap(size -> {
                                                TransactionFragrance tf = getTransactionFragrance(product, transaction, fragrance, size);
                                                return session.persist(tf);
                                            })
                                    );
                            insertions.add(insertion);
                        }

                        return Uni.combine().all().unis(insertions).discardItems().replaceWith(transaction);
                    });
        });
    }

    private static TransactionFragrance getTransactionFragrance(
            OrderProductDTO product,
            Transaction transaction,
            Fragrance fragrance,
            Size size
    ) {
        TransactionFragrance transactionFragrance = new TransactionFragrance();

        TransactionFragrancePK pk = new TransactionFragrancePK();
        pk.setTransactionId(transaction.getId());
        pk.setFragranceId(product.getFragranceId());
        pk.setSizeId(product.getSizeId());

        transactionFragrance.setId(pk);
        transactionFragrance.setTransaction(transaction);
        transactionFragrance.setFragrance(fragrance); // entidad gestionada
        transactionFragrance.setSize(size);           // entidad gestionada
        transactionFragrance.setQuantity(product.getQuantity());

        return transactionFragrance;
    }

}
