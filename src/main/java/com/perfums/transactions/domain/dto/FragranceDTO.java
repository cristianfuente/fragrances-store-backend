package com.perfums.transactions.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FragranceDTO {

    private Long id;
    private String name;
    private String description;
    private List<FragranceSizeDTO> sizes;
    private List<CatalogParameterDTO> parameters;

}
