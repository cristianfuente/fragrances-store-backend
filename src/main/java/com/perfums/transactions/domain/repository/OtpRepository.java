package com.perfums.transactions.domain.repository;

import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Transaction;
import io.smallrye.mutiny.Uni;

public interface OtpRepository {

    Uni<String> saveOtp(Transaction transaction);

}
