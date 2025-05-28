package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fragrances")
public class Fragrance extends PanacheEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Integer fragranceCatalogId;
    private String name;
    private String description;

}
