package com.perfums.transactions.infraestructure.adapters.postgresql.repositories.rest;

import com.perfums.transactions.domain.dto.EmailRequestDTO;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://fragrancesstore.online/")
@ApplicationScoped
@ClientHeaderParam(name = "X_API_KEY", value = "367526ff-14d9-4f69-bdbf-156e001c737f")
public interface EmailRepositoryRestClient {

    @POST
    @Path("/sender.php")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<String> send(EmailRequestDTO emailRequestDTO);

}
