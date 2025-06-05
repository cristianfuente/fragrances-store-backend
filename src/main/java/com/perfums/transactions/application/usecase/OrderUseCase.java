package com.perfums.transactions.application.usecase;

import com.perfums.transactions.domain.dto.OrderRequestDTO;
import com.perfums.transactions.domain.dto.OrderResponseDTO;
import io.smallrye.mutiny.Uni;

public interface OrderUseCase {

    Uni<String> generateRedirectUrl(String tokenValue);

    Uni<String> resolverClientRedirection(String tokenValue);

    Uni<OrderResponseDTO> processOrder(OrderRequestDTO request);

}
