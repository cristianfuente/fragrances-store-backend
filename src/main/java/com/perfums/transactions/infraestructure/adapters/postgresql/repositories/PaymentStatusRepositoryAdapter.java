package com.perfums.transactions.infraestructure.adapters.postgresql.repositories;

import com.perfums.transactions.domain.repository.PaymentStatusRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.rest.PaymentStatusRestClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class PaymentStatusRepositoryAdapter implements PaymentStatusRepository {

    @Inject
    @RestClient
    PaymentStatusRestClient paymentStatusRestClient;

    @Override
    public Uni<String> verifyTransaction(String codeTransaction){
        return paymentStatusRestClient.transactionStatus(codeTransaction);
    }

}
