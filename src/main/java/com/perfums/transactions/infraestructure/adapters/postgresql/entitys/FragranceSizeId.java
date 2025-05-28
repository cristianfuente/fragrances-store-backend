package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FragranceSizeId implements Serializable {

    @Column(name = "id_size")
    private Long sizeId;

    @Column(name = "id_fragrance")
    private Long fragranceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FragranceSizeId)) return false;
        FragranceSizeId that = (FragranceSizeId) o;
        return Objects.equals(fragranceId, that.fragranceId)
                && Objects.equals(sizeId, that.sizeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fragranceId, sizeId);
    }

}

