package com.perfums.transactions.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    private String apiKey;
    private Long paymentMethodId;
    private String email;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String phone;
    private String address;
    private String additionalAddressInfo;
    private String country;
    private String department;
    private String postalCode;
    private List<OrderProductDTO> products;

}
