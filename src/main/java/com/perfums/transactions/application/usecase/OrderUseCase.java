package com.perfums.transactions.application.usecase;

import com.perfums.transactions.domain.dto.OrderDTO;
import com.perfums.transactions.domain.dto.OrderRequestDTO;
import com.perfums.transactions.domain.dto.OrderCodeDTO;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface OrderUseCase {

    Uni<List<OrderDTO>> getOrderByStatus(String status);

    Uni<String> generateRedirectUrl(String tokenValue);

    Uni<String> resolverClientRedirection(String tokenValue);

    Uni<Void> sentOrder(String code);

    Uni<OrderCodeDTO> processOrder(OrderRequestDTO request);

}
