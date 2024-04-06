package com.foodapi.foodapi.model.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodapi.foodapi.DTO.product.ProductOutputDTO;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.interfaces.ToShort;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Product implements ToShort<ProductOutputDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private boolean active = true;
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    @Override
    public ProductOutputDTO toShort(ApiObjectMapper<ProductOutputDTO> apiObjectMapper) {
        return apiObjectMapper.convert(this, ProductOutputDTO.class);
    }
}
