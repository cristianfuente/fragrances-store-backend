package com.perfums.transactions.infraestructure.adapters.postgresql.repositories.rest;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://0tpdyogvs6.execute-api.us-east-1.amazonaws.com/default")
@ApplicationScoped
public interface PaymentStatusRestClient {


    @GET
    @Path("/consultar-pago/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<String> transactionStatus(@PathParam("id") String id);

}
