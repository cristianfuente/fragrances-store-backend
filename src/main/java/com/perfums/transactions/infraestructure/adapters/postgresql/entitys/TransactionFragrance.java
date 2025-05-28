package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "transactions_fragrances")
@Data
public class TransactionFragrance {

    @EmbeddedId
    private TransactionFragrancePK id;

    @ManyToOne
    @MapsId("transactionId")
    @JoinColumn(name = "id_transaction")
    private Transaction transaction;

    @ManyToOne
    @MapsId("fragranceId")
    @JoinColumn(name = "id_fragrance")
    private Fragrance fragrance;

    @ManyToOne
    @MapsId("sizeId")
    @JoinColumn(name = "id_size")
    private Size size;

    private Integer quantity;
}
