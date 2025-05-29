package com.perfums.transactions.domain.repository;

import com.perfums.transactions.domain.dto.OrderProductDTO;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.FragranceSize;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.FragranceSizeId;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface FragranceSizeRepository {
    Uni<Boolean> verifyAndReserveStock(List<OrderProductDTO> products);
    Uni<FragranceSize> findById(FragranceSizeId fragranceSizeId);
}
