package com.perfums.transactions.domain.repository;

import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Client;
import io.smallrye.mutiny.Uni;

public interface ClientRepository {

    Uni<Client> findByApiKey(String apiKey);

}
