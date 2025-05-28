package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "catalogs_parameters_fragrances",
            joinColumns = @JoinColumn(name = "id_fragrance"),
            inverseJoinColumns = @JoinColumn(name = "id_catalog_parameter")
    )
    private Set<CatalogParameter> catalogParameters;

    @OneToMany(mappedBy = "fragrance", fetch = FetchType.LAZY)
    private Set<FragranceSize> sizes;

}
