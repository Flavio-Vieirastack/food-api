package com.foodapi.foodapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private BigDecimal unitaryPrice;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    private String observation;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Orders orders;
    @OneToMany
    private List<Product> productList = new ArrayList<>();
}
