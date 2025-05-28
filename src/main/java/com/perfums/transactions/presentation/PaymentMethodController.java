package com.perfums.transactions.presentation;

import com.perfums.transactions.application.usecase.PaymentMethodUseCase;
import com.perfums.transactions.domain.dto.PaymentMethodDTO;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/payment-methods")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentMethodController {

    @Inject
    PaymentMethodUseCase useCase;

    @GET
    @WithSession
    public Uni<List<PaymentMethodDTO>> listAll() {
        return useCase.getAll();
    }

}
