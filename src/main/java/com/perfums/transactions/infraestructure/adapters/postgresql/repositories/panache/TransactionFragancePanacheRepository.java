package com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache;

import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.TransactionFragrance;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.TransactionFragrancePK;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TransactionFragancePanacheRepository implements PanacheRepositoryBase<TransactionFragrance, TransactionFragrancePK> {

    public Uni<List<TransactionFragrance>> findByTransactionId(Long transactionId) {
        return find("transaction.id = ?1", transactionId).list();
    }

}
