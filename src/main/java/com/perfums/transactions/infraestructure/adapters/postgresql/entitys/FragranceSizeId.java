package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import java.io.Serializable;
import java.util.Objects;

public class FragranceSizeId implements Serializable {

    private Long size;
    private Long fragrance;

    public FragranceSizeId() {}

    public FragranceSizeId(Long size, Long fragrance) {
        this.size = size;
        this.fragrance = fragrance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FragranceSizeId)) return false;
        FragranceSizeId that = (FragranceSizeId) o;
        return Objects.equals(size, that.size) && Objects.equals(fragrance, that.fragrance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, fragrance);
    }
}
