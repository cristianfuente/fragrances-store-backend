package com.perfums.transactions.infraestructure.adapters.postgresql.repositories;

import com.perfums.transactions.domain.dto.OrderProductDTO;
import com.perfums.transactions.domain.dto.OrderRequestDTO;
import com.perfums.transactions.domain.repository.TransactionRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Client;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.PaymentMethod;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Transaction;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache.TransactionPanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class TransactionRepositoryAdapter implements TransactionRepository {

    @Inject
    TransactionPanacheRepository transactionPanacheRepository;

    @Override
    public Uni<Transaction> createTransaction(
            Client client,
            List<OrderProductDTO> products,
            OrderRequestDTO requestDTO,
            PaymentMethod paymentMethod,
            String code,
            BigDecimal totalPayment
    ) {
        return transactionPanacheRepository.createTransaction(client, products, requestDTO, paymentMethod, code, totalPayment);
    }

}
