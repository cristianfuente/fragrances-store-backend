package com.perfums.transactions.application.usecase.impl;

import com.perfums.transactions.application.usecase.PaymentMethodUseCase;
import com.perfums.transactions.domain.dto.PaymentMethodDTO;
import com.perfums.transactions.domain.repository.PaymentMethodRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class PaymentMethodUseCaseImpl implements PaymentMethodUseCase {

    @Inject
    PaymentMethodRepository paymentMethodRepository;

    @Override
    public Uni<List<PaymentMethodDTO>> getAll() {
        return paymentMethodRepository.findAll()
                .map(list -> list.stream().map(pm -> {
                    log.info("Obteniendo lista de metodos de pago");
                    PaymentMethodDTO dto = new PaymentMethodDTO();
                    dto.setId(pm.getId());
                    dto.setName(pm.getName());
                    return dto;
                }).collect(Collectors.toList()));
    }

}
