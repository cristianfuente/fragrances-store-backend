package com.perfums.transactions.domain.repository;

import com.perfums.transactions.domain.dto.OrderProductDTO;
import com.perfums.transactions.domain.dto.OrderRequestDTO;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Client;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.PaymentMethod;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Transaction;
import io.smallrye.mutiny.Uni;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository {
    Uni<Transaction> createTransaction(
            Client client,
            List<OrderProductDTO> products,
            OrderRequestDTO requestDTO,
            PaymentMethod paymentMethod,
            String code,
            BigDecimal totalPayment
    );

    public Uni<Void> confirmPayment(Transaction transaction);

    Uni<Void> cancelTransactionAndRestoreStock(Long transactionId);

    Uni<Transaction> findById(Long idTransaction);

}
