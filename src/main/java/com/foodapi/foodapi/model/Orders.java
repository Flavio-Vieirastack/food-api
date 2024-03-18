package com.foodapi.foodapi.model;

import com.foodapi.foodapi.model.Embedded.Address;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal subTotal;
    private BigDecimal deliveryTax;
    private BigDecimal totalValue;
    @CreationTimestamp
    private LocalDateTime creationDate;
    private LocalDateTime confirmationDate;
    private LocalDateTime cancelDate;
    private LocalDateTime deliveryDate;
    @Embedded
    Address address;
    @ManyToOne
    UserClient userClient;
    @Enumerated
    private OrderStatus orderStatus;
    @OneToMany
    private List<PaymentType> paymentType;
    @OneToMany
    private List<OrderItem> orderItem;

    @ManyToOne
    private Restaurant restaurant;

    enum OrderStatus{
        CREATED,
        CONFIRMED,
        DELIVERED,
        CANCELED,
    }

}
