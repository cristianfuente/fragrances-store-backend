package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import java.io.Serializable;
import java.util.Objects;

public class CatalogParameterFragranceId implements Serializable {

    private Long fragrance;
    private Long catalog;
    private Long catalogParameter;

    public CatalogParameterFragranceId() {
    }

    public CatalogParameterFragranceId(Long fragrance, Long catalog, Long catalogParameter) {
        this.fragrance = fragrance;
        this.catalog = catalog;
        this.catalogParameter = catalogParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CatalogParameterFragranceId that)) return false;
        return Objects.equals(fragrance, that.fragrance)
                && Objects.equals(catalog, that.catalog)
                && Objects.equals(catalogParameter, that.catalogParameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fragrance, catalog, catalogParameter);
    }
}
