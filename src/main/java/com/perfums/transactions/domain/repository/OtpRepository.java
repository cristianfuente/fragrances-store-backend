package com.perfums.transactions.domain.repository;

import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Otp;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Transaction;
import io.smallrye.mutiny.Uni;

public interface OtpRepository {

    Uni<String> saveOtp(Transaction transaction);

    Uni<Otp> findByOtpValue(String otp);

    Uni<Void> deleteOtp(Otp otp);

    public Uni<Integer> updateOtp(Otp otp, String token);
}
