package com.perfums.transactions.application.usecase;

import com.perfums.transactions.domain.dto.FragranceDTO;
import com.perfums.transactions.domain.dto.FragranceFilterRequestDTO;
import com.perfums.transactions.domain.dto.FragrancePaginatedResponseDTO;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface FragranceUseCase {
    Uni<FragrancePaginatedResponseDTO> getAllFragrances(FragranceFilterRequestDTO request);
    Uni<FragranceDTO> getFragranceById(Long id);
}
