package com.foodapi.foodapi.model.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodapi.foodapi.DTO.order.OrderItemOutputDTO;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.interfaces.ToShort;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class OrderItem implements ToShort<OrderItemOutputDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private BigDecimal unitaryPrice;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    private String observation;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Orders orders;
    @OneToMany
    private List<Product> productList = new ArrayList<>();

    @Override
    public OrderItemOutputDTO toShort(ApiObjectMapper<OrderItemOutputDTO> apiObjectMapper) {
        return apiObjectMapper.convert(this, OrderItemOutputDTO.class);
    }
    
    public void totalPrice() {
        var quantityDecimal = new BigDecimal(quantity);
        setTotalPrice(quantityDecimal.multiply(unitaryPrice));
    }

    public void addProduct(Product product) {
        getProductList().add(product);
    }
}
