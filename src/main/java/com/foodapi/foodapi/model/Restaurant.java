package com.foodapi.foodapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Restaurant {
    @Id
    private Long id;
    private String name;
    @Column(name = "delivery_tax")
    private BigDecimal deliveryTax;
}
