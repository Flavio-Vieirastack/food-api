package com.foodapi.foodapi.model.models;

import com.foodapi.foodapi.DTO.order.OrderFilter;
import com.foodapi.foodapi.DTO.order.OrdersOutput;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.interfaces.ToShort;
import com.foodapi.foodapi.model.Embedded.Address;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Orders implements ToShort<OrdersOutput> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal subTotal;
    @Column(nullable = false)
    private BigDecimal deliveryTax;
    @Column(nullable = false)
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
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany
    private List<PaymentType> paymentType = new ArrayList<>();
    @OneToMany(cascade=CascadeType.PERSIST) //Resolve o erro: TransientPropertyValueException
    private List<OrderItem> orderItem = new ArrayList<>();

    @ManyToOne
    private Restaurant restaurant;

    public void addPaymentType(PaymentType paymentType) {
        getPaymentType().add(paymentType);
    }

    public enum OrderStatus {
        CREATED,
        CONFIRMED,
        DELIVERED,
        CANCELED,
    }
    @Override
    public OrdersOutput toShort(ApiObjectMapper<OrdersOutput> apiObjectMapper) {
        return apiObjectMapper.convert(this, OrdersOutput.class);
    }

    public OrderFilter filterOrder(ApiObjectMapper<OrderFilter> apiObjectMapper) {
        return apiObjectMapper.convert(this, OrderFilter.class);
    }

    public void confirm() {
        setOrderStatus(OrderStatus.CONFIRMED);
        setConfirmationDate(LocalDateTime.now());
    }

    public void delivered() {
        setOrderStatus(OrderStatus.DELIVERED);
        setDeliveryDate(LocalDateTime.now());
    }
    public void cancel() {
        setOrderStatus(OrderStatus.CANCELED);
        setCancelDate(LocalDateTime.now());
    }

}
