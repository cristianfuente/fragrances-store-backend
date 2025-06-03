package com.perfums.transactions.infraestructure.adapters.postgresql.repositories;

import com.perfums.transactions.domain.repository.OtpRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Otp;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Transaction;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache.OtpPanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class OtpRepositoryAdapter implements OtpRepository {

    @ConfigProperty(name = "payment.session.duration.seconds")
    String sessionDuration;

    @Inject
    OtpPanacheRepository otpPanacheRepository;

    @Override
    public Uni<String> saveOtp(Transaction transaction){
        Otp otp = new Otp();
        otp.setOtpToken(UUID.randomUUID().toString());
        otp.setTransaction(transaction);
        otp.setIssuedAt(Instant.now());
        otp.setExpiresAt(Instant.now()
                .plus(Duration.ofSeconds(Long.parseLong(sessionDuration))));
        return otpPanacheRepository.persist(otp).map(Otp::getOtpToken);
    }

    @Override
    public Uni<Otp> findByOtpValue(String otp){
        return otpPanacheRepository.findByOtpValue(otp);
    }

}
