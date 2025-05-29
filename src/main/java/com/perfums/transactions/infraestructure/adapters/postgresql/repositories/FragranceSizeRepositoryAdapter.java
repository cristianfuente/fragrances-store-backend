package com.perfums.transactions.infraestructure.adapters.postgresql.repositories;

import com.perfums.transactions.domain.dto.OrderProductDTO;
import com.perfums.transactions.domain.repository.FragranceSizeRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.FragranceSize;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.FragranceSizeId;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache.FragranceSizePanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class FragranceSizeRepositoryAdapter implements FragranceSizeRepository {

    @Inject
    FragranceSizePanacheRepository fragrancePanacheRepository;

    @Override
    public Uni<Boolean> verifyAndReserveStock(List<OrderProductDTO> products) {
        return fragrancePanacheRepository.verifyAndReserveStock(products);
    }

    @Override
    public Uni<FragranceSize> findById(FragranceSizeId fragranceSizeId) {
        return fragrancePanacheRepository.findById(fragranceSizeId);
    }

}
