package com.perfums.transactions.application.usecase;

import com.perfums.transactions.domain.dto.FraganceDTO;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface FragranceUseCase {
    Uni<List<FraganceDTO>> getAllFragrances();
    Uni<FraganceDTO> getFragranceById(Long id);
}
