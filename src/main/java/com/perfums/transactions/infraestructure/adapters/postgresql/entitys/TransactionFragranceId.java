package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import java.io.Serializable;
import java.util.Objects;

public class TransactionFragranceId implements Serializable {

    private Long transaction;
    private Long fragranceSize;

    public TransactionFragranceId() {
    }

    public TransactionFragranceId(Long transaction, Long fragranceSize) {
        this.transaction = transaction;
        this.fragranceSize = fragranceSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionFragranceId that)) return false;
        return Objects.equals(transaction, that.transaction) &&
                Objects.equals(fragranceSize, that.fragranceSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction, fragranceSize);
    }
}
