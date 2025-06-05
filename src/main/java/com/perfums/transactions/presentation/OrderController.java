package com.perfums.transactions.presentation;

import com.perfums.transactions.application.usecase.OrderUseCase;
import com.perfums.transactions.domain.dto.ErrorDTO;
import com.perfums.transactions.domain.dto.OrderRequestDTO;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
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

@Slf4j
@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

    @Inject
    OrderUseCase orderUseCase;

    @POST
    @WithTransaction
    public Uni<Response> createOrder(OrderRequestDTO request) {
        return orderUseCase.processOrder(request)
                .map(Response::ok)
                .map(Response.ResponseBuilder::build)
                .onFailure()
                .recoverWithItem(throwable -> Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new ErrorDTO(throwable.getMessage()))
                        .build());
    }

    @GET
    @Path("/payment/{otp}")
    @WithTransaction
    public Uni<Response> redirectToPayment(@PathParam("otp") String otp) {
        return orderUseCase.generateRedirectUrl(otp)
                .map(url -> Response
                        .status(Response.Status.FOUND) // 302 Redirect
                        .header("Location", url)
                        .build())
                .onFailure()
                .recoverWithItem(throwable -> Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorDTO("No se pudo generar la redirección: " + throwable.getMessage()))
                        .build());
    }

    @GET
    @Path("/checkout/{otp}")
    @WithTransaction
    public Uni<Response> resolveClientRedirection(@PathParam("otp") String otp) {
        return orderUseCase.resolverClientRedirection(otp)
                .map(url -> Response
                        .status(Response.Status.FOUND) // 302 Redirect
                        .header("Location", url)
                        .build())
                .onFailure()
                .recoverWithItem(throwable -> Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorDTO("No se pudo generar la redirección: " + throwable.getMessage()))
                        .build());
    }

}
