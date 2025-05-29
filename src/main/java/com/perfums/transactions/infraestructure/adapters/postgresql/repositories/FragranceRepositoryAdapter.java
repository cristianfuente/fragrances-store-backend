package com.perfums.transactions.infraestructure.adapters.postgresql.repositories;

import com.perfums.transactions.domain.dto.CatalogParameterDTO;
import com.perfums.transactions.domain.dto.FragranceFilterRequestDTO;
import com.perfums.transactions.domain.repository.FragranceRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Fragrance;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache.FragrancePanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class FragranceRepositoryAdapter implements FragranceRepository {

    @Inject
    FragrancePanacheRepository panacheRepo;

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
        int page = Optional.ofNullable(request.getPage()).orElse(0);
        int size = Optional.ofNullable(request.getSize()).orElse(10);

        return panacheRepo.findByFilters(ids, search, page, size);
    }

    @Override
    public Uni<Long> countFiltered(List<CatalogParameterDTO> filters, String searchText) {
        Set<Long> ids = filters != null
                ? filters.stream()
                .filter(Objects::nonNull)
                .map(CatalogParameterDTO::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
                : Set.of();

        return panacheRepo.countFiltered(ids, searchText);
    }

    @Override
    public Uni<Fragrance> findById(Long id) {
        return panacheRepo.findById(id);
    }

}
