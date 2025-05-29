package com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache;

import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.PaymentMethod;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentMethodPanacheRepository implements PanacheRepositoryBase<PaymentMethod, Long> {
}
