package com.perfums.transactions.infraestructure.adapters.postgresql.repositories;

import com.perfums.transactions.domain.repository.ClientRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Client;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache.ClientPanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ClientRepositoryAdapter implements ClientRepository {

    @Inject
    ClientPanacheRepository panacheRepo;

    @Override
    public Uni<Client> findByApiKey(String apiKey) {
        return panacheRepo.find("apiKey", apiKey).firstResult();
    }

}
