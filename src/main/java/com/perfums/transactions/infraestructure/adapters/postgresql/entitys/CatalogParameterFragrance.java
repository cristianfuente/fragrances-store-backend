package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "catalogs_parameters_fragrances")
@Data
@IdClass(CatalogParameterFragranceId.class)
public class CatalogParameterFragrance {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_fragrance")
    private Fragrance fragrance;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_catalog")
    private Catalog catalog;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_catalog_parameter")
    private CatalogParameter catalogParameter;
}
