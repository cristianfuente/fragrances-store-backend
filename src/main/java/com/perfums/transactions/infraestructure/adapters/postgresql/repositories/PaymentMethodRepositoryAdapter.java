package com.perfums.transactions.infraestructure.adapters.postgresql.repositories;

import com.perfums.transactions.domain.repository.PaymentMethodRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.PaymentMethod;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache.PaymentMethodPanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PaymentMethodRepositoryAdapter implements PaymentMethodRepository {

    @Inject
    PaymentMethodPanacheRepository panacheRepository;

    @Override
    public Uni<List<PaymentMethod>> findAll() {
        return panacheRepository.findAll().list();
    }

    @Override
    public Uni<PaymentMethod> findById(Long id) {
        return panacheRepository.findById(id);
    }

}
