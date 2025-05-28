package com.perfums.transactions.application.usecase;

import com.perfums.transactions.domain.dto.PaymentMethodDTO;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface PaymentMethodUseCase {
    Uni<List<PaymentMethodDTO>> getAll();
}
