package com.perfums.transactions.application.usecase.impl;

import com.perfums.transactions.application.usecase.CatalogUseCase;
import com.perfums.transactions.domain.dto.CatalogFilterDTO;
import com.perfums.transactions.domain.dto.CatalogParameterDTO;
import com.perfums.transactions.domain.repository.CatalogRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CatalogUseCaseImpl implements CatalogUseCase {

    @Inject
    CatalogRepository catalogRepository;

    @Override
    public Uni<List<CatalogFilterDTO>> getCatalogFilters() {
        return catalogRepository.findAllWithParameters().onItem().transform(catalogs ->
                catalogs.stream().map(catalog -> {
                    CatalogFilterDTO dto = new CatalogFilterDTO();
                    dto.setId(catalog.getId());
                    dto.setName(catalog.getName());
                    dto.setParameters(catalog.getParameters().stream().map(param -> {
                        CatalogParameterDTO p = new CatalogParameterDTO();
                        p.setId(param.getId());
                        p.setName(param.getName());
                        return p;
                    }).collect(Collectors.toList()));
                    return dto;
                }).collect(Collectors.toList())
        );
    }

}
