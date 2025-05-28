package com.perfums.transactions.infraestructure.adapters.postgresql.repositories;

import com.perfums.transactions.domain.repository.FragranceRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Fragrance;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache.FragancePanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class FragranceRepositoryAdapter implements FragranceRepository {

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
