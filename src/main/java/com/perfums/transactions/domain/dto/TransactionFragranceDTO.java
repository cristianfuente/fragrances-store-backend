package com.perfums.transactions.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class TransactionFragranceDTO {
    private Long fragranceId;
    private Long sizeId;
    private Integer quantity;
}
