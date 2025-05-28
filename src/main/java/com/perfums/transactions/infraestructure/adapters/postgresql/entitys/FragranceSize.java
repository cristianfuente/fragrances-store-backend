package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "fragrances_sizes")
@Data
@IdClass(FragranceSizeId.class)
public class FragranceSize {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_size")
    private Size size;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_fragrance")
    private Fragrance fragrance;

    private Integer stock;
    private Double price;

    @Column(name = "image_id")
    private String imageId;
}
