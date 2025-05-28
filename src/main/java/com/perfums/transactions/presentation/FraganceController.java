package com.perfums.transactions.presentation;

import com.perfums.transactions.application.usecase.FragranceUseCase;
import com.perfums.transactions.domain.dto.FraganceDTO;
import com.perfums.transactions.domain.dto.FragranceFilterRequestDTO;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Path("/fragrances")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FraganceController {

    @Inject
    FragranceUseCase fragranceUseCase;

    @POST
    @WithSession
    public Uni<List<FraganceDTO>> getAll(FragranceFilterRequestDTO request) {
        return fragranceUseCase.getAllFragrances(request);
    }

    @GET
    @Path("/{id}")
    @WithSession
    public Uni<Response> getById(@PathParam("id") Long id) {
        return fragranceUseCase.getFragranceById(id)
                .onItem().ifNotNull().transform(dto -> Response.ok(dto).build())
                .onItem().ifNull().continueWith(Response.status(Response.Status.NOT_FOUND)::build);
    }

}
