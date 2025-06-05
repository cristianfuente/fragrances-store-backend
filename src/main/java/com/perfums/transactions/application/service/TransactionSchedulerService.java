package com.perfums.transactions.application.service;

import com.perfums.transactions.domain.models.StateType;
import com.perfums.transactions.domain.repository.PaymentStatusRepository;
import com.perfums.transactions.domain.repository.TransactionRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.concurrent.TimeUnit;

@ApplicationScoped
@Slf4j
public class TransactionSchedulerService {

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    Vertx vertx;

    @Inject
    PaymentStatusRepository paymentStatusRepository;

    @ConfigProperty(name = "payment.session.duration.seconds")
    String sessionDuration;

    public void scheduleCancellation(Long transactionId) {
        log.info("Programando verificación de transacción {}", transactionId);

        vertx.setTimer(TimeUnit.SECONDS.toMillis(Long.parseLong(sessionDuration)), id -> {
            log.info("Ejecutando verificación de transacción {}", transactionId);

            vertx.runOnContext(ignored -> transactionRepository.findById(transactionId)
                    .flatMap(transaction -> {
                        if (transaction.getState().equals(StateType.PAID)) {
                            return Uni.createFrom().voidItem();
                        }
                        return paymentStatusRepository.verifyTransaction(transaction.getCode())
                                .onItem().transformToUni(response -> {
                                    log.info("Se ha encontrado el pago para la transaccion");
                                    return transactionRepository.confirmPayment(transaction);
                                })
                                .onFailure()
                                .recoverWithUni(th -> transactionRepository
                                        .cancelTransactionAndRestoreStock(transactionId)
                                        .invoke(() -> log.info("Transacción {} cancelada por falta de pago", transaction.getCode())));
                    })
                    .subscribe().with(
                            ignoredResult -> log.info("Verificación completa"),
                            error -> log.error("Error al cancelar transacción", error)
                    ));
        });
    }

}
