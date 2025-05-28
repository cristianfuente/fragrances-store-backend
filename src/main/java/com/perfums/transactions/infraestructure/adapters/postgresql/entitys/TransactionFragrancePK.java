package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionFragrancePK implements Serializable {
    private Long transactionId;
    private Long fragranceId;
    private Long sizeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionFragrancePK)) return false;
        TransactionFragrancePK that = (TransactionFragrancePK) o;
        return Objects.equals(transactionId, that.transactionId)
                && Objects.equals(fragranceId, that.fragranceId)
                && Objects.equals(sizeId, that.sizeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, fragranceId, sizeId);
    }
}
