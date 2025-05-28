package com.perfums.transactions.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FragranceFilterRequestDTO {
    private List<CatalogParameterDTO> filters;
    private String searchText;
    private Integer page;
    private Integer size;
    private String apiKey;
}
