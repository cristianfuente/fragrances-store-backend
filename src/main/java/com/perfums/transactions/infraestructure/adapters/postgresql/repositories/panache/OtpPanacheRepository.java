package com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache;

import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Otp;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OtpPanacheRepository implements PanacheRepositoryBase<Otp, Long> {
}
