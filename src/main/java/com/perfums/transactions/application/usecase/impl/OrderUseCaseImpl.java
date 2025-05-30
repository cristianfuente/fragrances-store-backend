package com.perfums.transactions.application.usecase.impl;

import com.perfums.transactions.application.service.TransactionSchedulerService;
import com.perfums.transactions.application.usecase.OrderUseCase;
import com.perfums.transactions.domain.dto.OrderProductDTO;
import com.perfums.transactions.domain.dto.OrderRequestDTO;
import com.perfums.transactions.domain.dto.OrderResponseDTO;
import com.perfums.transactions.domain.repository.ClientRepository;
import com.perfums.transactions.domain.repository.FragranceSizeRepository;
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

    @Override
    public Uni<OrderResponseDTO> processOrder(OrderRequestDTO request) {
        return clientRepository.findByApiKey(request.getApiKey())
                .flatMap(client -> {
                    double margin = client.getMargin() != null ? client.getMargin() : 0.0;

                    return calculateTotalSequentially(request.getProducts(), margin)
                            .flatMap(total ->
                                    fragranceSizeRepository.verifyAndReserveStock(request.getProducts())
                                            .flatMap(isAvailable -> {
                                                if (!isAvailable) {
                                                    return Uni.createFrom().failure(new IllegalStateException("Insufficient stock for one or more products."));
                                                }

                                                return paymentMethodRepository.findById(request.getPaymentMethodId())
                                                        .flatMap(paymentMethod -> {
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
                                                                    .map(transaction -> {
                                                                        OrderResponseDTO response = new OrderResponseDTO();
                                                                        response.setCode(code);
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
        if (index >= products.size()) {
            return Uni.createFrom().item(accumulator);
        }

        OrderProductDTO current = products.get(index);
        FragranceSizeId id = new FragranceSizeId(current.getSizeId(), current.getFragranceId());

        return fragranceSizeRepository.findById(id)
                .flatMap(fragranceSize -> {
                    BigDecimal basePrice = fragranceSize.getPrice();
                    BigDecimal priceWithMargin = basePrice.add(basePrice.multiply(BigDecimal.valueOf(margin)));
                    BigDecimal total = priceWithMargin.multiply(BigDecimal.valueOf(current.getQuantity()));
                    return calculateTotalRecursive(products, margin, index + 1, accumulator.add(total));
                });
    }


}
