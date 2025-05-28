package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "api_key")
    private String apiKey;

    private Double margin;
    private String status;
    private String code;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

