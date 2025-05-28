package com.perfums.transactions.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CatalogFilterDTO {
    private Long id;
    private String name;
    private List<CatalogParameterDTO> parameters;
}
