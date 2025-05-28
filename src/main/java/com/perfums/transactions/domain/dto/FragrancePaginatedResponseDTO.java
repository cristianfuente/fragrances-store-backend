package com.perfums.transactions.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FragrancePaginatedResponseDTO {

    private int totalElements;
    private int totalPages;
    private int size;
    private int currentPage;
    private double margin;
    private List<FragranceDTO> data;

}
