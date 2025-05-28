package com.perfums.transactions.application.usecase.impl;

import com.perfums.transactions.application.usecase.FragranceUseCase;
import com.perfums.transactions.domain.dto.FraganceDTO;
import com.perfums.transactions.domain.dto.FragranceFilterRequestDTO;
import com.perfums.transactions.domain.repository.FragranceRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Fragrance;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FragranceUseCaseImpl implements FragranceUseCase {

    @Inject
    FragranceRepository fragranceRepository;

    @Override
    public Uni<List<FraganceDTO>> getAllFragrances(FragranceFilterRequestDTO request) {
        return fragranceRepository.findFiltered(request)
                .onItem().transform(fragrances ->
                        fragrances.stream().map(this::toDTO).collect(Collectors.toList())
                );
    }

    @Override
    public Uni<FraganceDTO> getFragranceById(Long id) {
        return fragranceRepository.findById(id)
                .onItem().ifNotNull().transform(this::toDTO);
    }

    private FraganceDTO toDTO(Fragrance fragrance) {
        FraganceDTO dto = new FraganceDTO();
        dto.setId(fragrance.getId());
        dto.setName(fragrance.getName());
        dto.setDescription(fragrance.getDescription());
        return dto;
    }

}
