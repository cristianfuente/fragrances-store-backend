package com.perfums.transactions.infraestructure.adapters.postgresql.entitys;

import com.perfums.transactions.domain.models.StateType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private StateType state;

    @Column(name = "total_payment")
    private BigDecimal totalPayment;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    private String email;
    private String address;
    private String code;

    private String firstName;
    private String lastName;
    private String documentNumber;
    private String phone;
    private String additionalAddressInfo;
    private String country;
    private String department;
    private String postalCode;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TransactionFragrance> fragrances = new ArrayList<>();

}
