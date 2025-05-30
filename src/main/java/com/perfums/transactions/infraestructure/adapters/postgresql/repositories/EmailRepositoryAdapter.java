package com.perfums.transactions.infraestructure.adapters.postgresql.repositories;

import com.perfums.transactions.domain.dto.EmailRequestDTO;
import com.perfums.transactions.domain.repository.EmailRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.rest.EmailRepositoryRestClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class EmailRepositoryAdapter implements EmailRepository {

    @Inject
    @RestClient
    EmailRepositoryRestClient emailRepositoryRestClient;

    @Override
    public Uni<String> send(EmailRequestDTO requestDTO) {
        return emailRepositoryRestClient.send(requestDTO);
    }

}
