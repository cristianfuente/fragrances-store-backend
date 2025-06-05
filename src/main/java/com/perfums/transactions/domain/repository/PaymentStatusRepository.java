package com.perfums.transactions.domain.repository;

import io.smallrye.mutiny.Uni;

public interface PaymentStatusRepository {
    Uni<String> verifyTransaction(String codeTransaction);
}
