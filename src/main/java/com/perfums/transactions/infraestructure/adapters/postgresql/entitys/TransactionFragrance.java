package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "transactions_fragrances")
@Data
@IdClass(TransactionFragranceId.class)
public class TransactionFragrance {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_transaction")
    private Transaction transaction;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_size", referencedColumnName = "id_size"),
            @JoinColumn(name = "id_fragrance", referencedColumnName = "id_fragrance")
    })
    private FragranceSize fragranceSize;

    private Integer quantity;
}
