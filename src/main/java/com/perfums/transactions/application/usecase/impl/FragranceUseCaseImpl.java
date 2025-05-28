package com.perfums.transactions.application.usecase.impl;

import com.perfums.transactions.application.usecase.FragranceUseCase;
import com.perfums.transactions.domain.dto.CatalogParameterDTO;
import com.perfums.transactions.domain.dto.FragranceDTO;
import com.perfums.transactions.domain.dto.FragranceFilterRequestDTO;
import com.perfums.transactions.domain.dto.FragrancePaginatedResponseDTO;
import com.perfums.transactions.domain.dto.FragranceSizeDTO;
import com.perfums.transactions.domain.repository.ClientRepository;
import com.perfums.transactions.domain.repository.FragranceRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Fragrance;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Size;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class FragranceUseCaseImpl implements FragranceUseCase {

    @Inject
    FragranceRepository fragranceRepository;

    @Inject
    ClientRepository clientRepository;

    @Override
    public Uni<FragrancePaginatedResponseDTO> getAllFragrances(FragranceFilterRequestDTO request) {
        return clientRepository.findByApiKey(request.getApiKey())
                .flatMap(client -> {
                    double margin = client.getMargin() != null ? client.getMargin() : 0.0;
                    int page = Optional.ofNullable(request.getPage()).orElse(0);
                    int size = Optional.ofNullable(request.getSize()).orElse(10);

                    return fragranceRepository.findFiltered(request)
                            .flatMap(fragrances ->
                                    fragranceRepository.countFiltered(request.getFilters(), request.getSearchText())
                                            .map(total -> {
                                                List<FragranceDTO> fragranceDTOs = fragrances.stream()
                                                        .map(f -> toDTO(f, margin))
                                                        .collect(Collectors.toList());

                                                FragrancePaginatedResponseDTO response = new FragrancePaginatedResponseDTO();
                                                response.setTotalElements(total.intValue());
                                                response.setTotalPages((int) Math.ceil((double) total / size));
                                                response.setCurrentPage(page);
                                                response.setSize(size);
                                                response.setMargin(margin);
                                                response.setData(fragranceDTOs);
                                                return response;
                                            })
                            );
                });
    }

    @Override
    public Uni<FragranceDTO> getFragranceById(Long id) {
        return fragranceRepository.findById(id)
                .onItem().ifNotNull().transform(f -> toDTO(f, 0)); // sin margen
    }

    private FragranceDTO toDTO(Fragrance fragrance, double margin) {
        FragranceDTO dto = new FragranceDTO();
        dto.setId(fragrance.getId());
        dto.setName(fragrance.getName());
        dto.setDescription(fragrance.getDescription());

        List<FragranceSizeDTO> sizes = fragrance.getSizes().stream().map(fs -> {
            Size size = fs.getSize();
            Double originalPrice = fs.getPrice();

            BigDecimal originalPriceDecimal = BigDecimal.valueOf(fs.getPrice());
            BigDecimal priceWithMargin = originalPriceDecimal
                    .add(originalPriceDecimal.multiply(BigDecimal.valueOf(margin)));

            FragranceSizeDTO sizeDTO = new FragranceSizeDTO();
            sizeDTO.setSizeId(size.getId());
            sizeDTO.setLabel(size.getLabel());
            sizeDTO.setSize(size.getSize());
            sizeDTO.setUnit(size.getUnit());
            sizeDTO.setOriginalPrice(originalPrice);
            sizeDTO.setPrice(priceWithMargin.doubleValue());
            sizeDTO.setStock(fs.getStock());
            sizeDTO.setImageUrl(fs.getImageId());

            return sizeDTO;
        }).collect(Collectors.toList());
        dto.setSizes(sizes);

        List<CatalogParameterDTO> params = fragrance.getCatalogParameters().stream()
                .map(cp -> {
                    CatalogParameterDTO paramDTO = new CatalogParameterDTO();
                    paramDTO.setId(cp.getId());
                    paramDTO.setName(cp.getName());
                    return paramDTO;
                }).toList();
        dto.setParameters(params);

        return dto;
    }


}
