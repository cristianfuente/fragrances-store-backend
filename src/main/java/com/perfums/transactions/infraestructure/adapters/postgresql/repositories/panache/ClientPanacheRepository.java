package com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache;

import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Client;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClientPanacheRepository implements PanacheRepositoryBase<Client, Long> {

    public Uni<Client> findByApiKeySync(String apiKey) {
        return find("apiKey", apiKey).firstResult();
    }

}
