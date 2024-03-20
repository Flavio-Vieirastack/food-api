package com.foodapi.foodapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String description;
}
