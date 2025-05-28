package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fragrances")
@Getter
@Setter
public class Fragrance {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "id_fragrance_id_catalog")
    private Integer fragranceCatalogId;
    private String name;
    private String description;

}
