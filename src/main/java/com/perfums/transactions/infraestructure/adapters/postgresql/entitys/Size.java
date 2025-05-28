    package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.Table;
    import lombok.Data;

    @Entity
    @Table(name = "size")
    @Data
    public class Size {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Double size;
        private String unit;
        private String label;
    }

