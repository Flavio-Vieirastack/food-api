package com.foodapi.foodapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.foodapi.foodapi.model.Embedded.Address;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "delivery_tax", nullable = false)
    private BigDecimal deliveryTax;
    @ManyToOne
    private Kitchen kitchen; //Muitos restaurantes tem uma cozinha
    @ManyToMany
    @JoinTable(name = "restaurant_payment_type",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_type_id")
    )
    @JsonIgnore
    private List<PaymentType> paymentTypes = new ArrayList<>();
    @Embedded
    @JsonIgnore
    private Address address;
    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(columnDefinition = "datetime")
    private LocalDateTime updatedAt;
    @OneToMany
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Product> products = new ArrayList<>();
}
