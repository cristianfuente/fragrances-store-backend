package com.perfums.transactions.application.usecase;

import com.perfums.transactions.domain.dto.CatalogFilterDTO;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface CatalogUseCase {

    Uni<List<CatalogFilterDTO>> getCatalogFilters();

}
