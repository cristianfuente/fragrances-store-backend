package com.perfums.transactions.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FragranceSizeDTO {
    private Long sizeId;
    private String unit;
    private String label;
    private Double size;
    private BigDecimal originalPrice;
    private Double price;
    private Integer stock;
    private String imageUrl;
}