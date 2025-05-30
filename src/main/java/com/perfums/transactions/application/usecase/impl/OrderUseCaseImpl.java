package com.perfums.transactions.application.usecase.impl;

import com.perfums.transactions.application.service.TransactionSchedulerService;
import com.perfums.transactions.application.usecase.OrderUseCase;
import com.perfums.transactions.domain.dto.OrderProductDTO;
import com.perfums.transactions.domain.dto.OrderRequestDTO;
import com.perfums.transactions.domain.dto.OrderResponseDTO;
import com.perfums.transactions.domain.repository.ClientRepository;
import com.perfums.transactions.domain.repository.FragranceSizeRepository;
import com.perfums.transactions.domain.repository.OtpRepository;
import com.perfums.transactions.domain.repository.PaymentMethodRepository;
import com.perfums.transactions.domain.repository.TransactionRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.FragranceSizeId;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Slf4j
@ApplicationScoped
public class OrderUseCaseImpl implements OrderUseCase {

    @Inject
    ClientRepository clientRepository;

    @Inject
    PaymentMethodRepository paymentMethodRepository;

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    FragranceSizeRepository fragranceSizeRepository;

    @Inject
    TransactionSchedulerService schedulerService;

    @Inject
    OtpRepository otpRepository;

    @Override
    public Uni<OrderResponseDTO> processOrder(OrderRequestDTO request) {
        return clientRepository.findByApiKey(request.getApiKey())
                .flatMap(client -> {
                    log.info("Cliente encontrado para transaccion");
                    double margin = client.getMargin() != null ? client.getMargin() : 0.0;

                    return calculateTotalSequentially(request.getProducts(), margin)
                            .flatMap(total ->
                                    fragranceSizeRepository.verifyAndReserveStock(request.getProducts())
                                            .flatMap(isAvailable -> {
                                                log.info("verificacion de stock de fragancias");
                                                if (!isAvailable) {
                                                    log.error("Stock insuficiente");
                                                    return Uni.createFrom().failure(new IllegalStateException("Insufficient stock for one or more products."));
                                                }

                                                log.info("Stock disponible para transaccion");
                                                return paymentMethodRepository.findById(request.getPaymentMethodId())
                                                        .flatMap(paymentMethod -> {
                                                            log.info("Verificacion metodo de pago completa");
                                                            String code = generateTransactionCode();
                                                            return transactionRepository.createTransaction(
                                                                            client,
                                                                            request.getProducts(),
                                                                            request,
                                                                            paymentMethod,
                                                                            code,
                                                                            total
                                                                    ).invoke(transaction -> schedulerService
                                                                            .scheduleCancellation(transaction.getId()))
                                                                    .flatMap(transaction -> otpRepository.saveOtp(transaction))
                                                                    .map(otp -> {
                                                                        log.info("Se ha creado la transaccion");
                                                                        OrderResponseDTO response = new OrderResponseDTO();
                                                                        response.setCode(otp);
                                                                        return response;
                                                                    });
                                                        });
                                            })
                            );
                });
    }

    private String generateTransactionCode() {
        return new Random().ints(9, 0, 10)
                .mapToObj(String::valueOf)
                .reduce("", String::concat);
    }

    private Uni<BigDecimal> calculateTotalSequentially(List<OrderProductDTO> products, double margin) {
        return calculateTotalRecursive(products, margin, 0, BigDecimal.ZERO);
    }

    private Uni<BigDecimal> calculateTotalRecursive(List<OrderProductDTO> products, double margin, int index, BigDecimal accumulator) {
        log.info("Calculando total precio para transaccion");
        if (index >= products.size()) {
            return Uni.createFrom().item(accumulator);
        }

        OrderProductDTO current = products.get(index);
        FragranceSizeId id = new FragranceSizeId(current.getSizeId(), current.getFragranceId());

        return fragranceSizeRepository.findById(id)
                .flatMap(fragranceSize -> {
                    log.info("Obteniendo informacion de fragancia y tamano");
                    BigDecimal basePrice = fragranceSize.getPrice();
                    BigDecimal priceWithMargin = basePrice.add(basePrice.multiply(BigDecimal.valueOf(margin)));
                    BigDecimal total = priceWithMargin.multiply(BigDecimal.valueOf(current.getQuantity()));
                    return calculateTotalRecursive(products, margin, index + 1, accumulator.add(total));
                });
    }


}
