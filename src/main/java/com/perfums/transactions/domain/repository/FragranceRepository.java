package com.perfums.transactions.domain.repository;

import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Fragrance;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface FragranceRepository {
    Uni<List<Fragrance>> listAll();

    Uni<Fragrance> findById(Long id);
}
