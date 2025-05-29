package com.perfums.transactions.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {

    private Long fragranceId;
    private Long sizeId;
    private Integer quantity;

}
