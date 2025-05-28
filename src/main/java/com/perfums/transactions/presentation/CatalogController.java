package com.perfums.transactions.presentation;

import com.perfums.transactions.application.usecase.CatalogUseCase;
import com.perfums.transactions.domain.dto.CatalogFilterDTO;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/catalogs")
public class CatalogController {

    @Inject
    CatalogUseCase catalogUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @WithSession
    public Uni<List<CatalogFilterDTO>> getCatalogFilters() {
        return catalogUseCase.getCatalogFilters();
    }

}
