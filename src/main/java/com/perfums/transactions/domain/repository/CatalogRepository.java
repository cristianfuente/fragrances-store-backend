package com.perfums.transactions.domain.repository;

import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Catalog;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface CatalogRepository {
    Uni<List<Catalog>> findAllWithParameters();
}
