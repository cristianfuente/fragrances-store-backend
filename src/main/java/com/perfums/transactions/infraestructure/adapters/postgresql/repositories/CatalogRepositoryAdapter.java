package com.perfums.transactions.infraestructure.adapters.postgresql.repositories;

import com.perfums.transactions.domain.repository.CatalogRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Catalog;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache.CatalogPanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CatalogRepositoryAdapter implements CatalogRepository {

    @Inject
    CatalogPanacheRepository panacheRepository;

    @Override
    public Uni<List<Catalog>> findAllWithParameters() {
        return panacheRepository.find("SELECT DISTINCT c FROM Catalog c LEFT JOIN FETCH c.parameters")
                .list();
    }

}
