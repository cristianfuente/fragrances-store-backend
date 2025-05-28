package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "fragrances_sizes")
@Data
public class FragranceSize {

    @EmbeddedId
    private FragranceSizeId id;

    @ManyToOne
    @MapsId("fragranceId")
    @JoinColumn(name = "id_fragrance")
    private Fragrance fragrance;

    @ManyToOne
    @MapsId("sizeId")
    @JoinColumn(name = "id_size")
    private Size size;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "image_id")
    private String imageId;

}
