package com.perfums.transactions.domain.repository;

import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.PaymentMethod;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface PaymentMethodRepository {

    Uni<List<PaymentMethod>> findAll();

}
