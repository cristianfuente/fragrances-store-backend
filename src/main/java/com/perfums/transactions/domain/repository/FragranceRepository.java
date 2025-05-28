package com.perfums.transactions.domain.repository;

import com.perfums.transactions.domain.dto.CatalogParameterDTO;
import com.perfums.transactions.domain.dto.FragranceFilterRequestDTO;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Fragrance;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface FragranceRepository {
    Uni<List<Fragrance>> findFiltered(FragranceFilterRequestDTO request);

    Uni<Long> countFiltered(List<CatalogParameterDTO> filters, String searchText);

    Uni<Fragrance> findById(Long id);
}
