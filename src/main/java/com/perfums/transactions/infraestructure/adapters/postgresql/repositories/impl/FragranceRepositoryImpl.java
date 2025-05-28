package com.perfums.transactions.infraestructure.adapters.postgresql.repositories.impl;

import com.perfums.transactions.domain.repository.FragranceRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Fragrance;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.FragancePanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class FragranceRepositoryImpl implements FragranceRepository {

    @Inject
    FragancePanacheRepository panacheRepo;

    @Override
    public Uni<List<Fragrance>> listAll() {
        return panacheRepo.listAll();
    }

    @Override
    public Uni<Fragrance> findById(Long id) {
        return panacheRepo.findById(id);
    }

}
