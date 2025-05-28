package com.perfums.transactions.infraestructure.adapters.postgresql.repositories;

import com.perfums.transactions.domain.dto.CatalogParameterDTO;
import com.perfums.transactions.domain.dto.FragranceFilterRequestDTO;
import com.perfums.transactions.domain.repository.FragranceRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Fragrance;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache.FragancePanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class FragranceRepositoryAdapter implements FragranceRepository {

    @Inject
    FragancePanacheRepository panacheRepo;

    @Override
    public Uni<List<Fragrance>> findFiltered(FragranceFilterRequestDTO request) {
        Set<Long> ids = (request != null && request.getFilters() != null)
                ? request.getFilters().stream()
                .filter(Objects::nonNull)
                .map(CatalogParameterDTO::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
                : Set.of();

        String search = request != null ? request.getSearchText() : null;

        return panacheRepo.findByFilters(ids, search);
    }

    @Override
    public Uni<Fragrance> findById(Long id) {
        return panacheRepo.findById(id);
    }

}
