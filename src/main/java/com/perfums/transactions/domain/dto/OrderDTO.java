package com.perfums.transactions.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private String clientName;
    private String firstName;
    private String lastName;
    private String codeTransaction;
    private BigDecimal totalPayment;
    private String state;
    private String email;
    private String address;
    private String additionalAddressInfo;
    private String phone;
    private String country;
    private String department;
    private String postalCode;
    private LocalDateTime createAt;

    private List<FragranceItemDTO> fragrances;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FragranceItemDTO {
        private String name;
        private String sizeLabel;
        private Integer quantity;
    }
}
