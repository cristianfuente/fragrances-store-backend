package com.perfums.transactions.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FraganceDTO {

    private Long id;
    private String name;
    private String description;
    private List<FraganceSizeDTO> fraganceSizes;

}
