package com.foodapi.foodapi.DTO.order;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.foodapi.foodapi.model.Embedded.Address;
import com.foodapi.foodapi.model.models.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@JsonFilter("orderFilter")
public record OrderFilter(
        Long id,
        BigDecimal subTotal,
        BigDecimal deliveryTax,
        BigDecimal totalValue,
        LocalDateTime creationDate,
        LocalDateTime confirmationDate,
        LocalDateTime cancelDate,
        LocalDateTime deliveryDate,
        Address address,
        UserClient userClient,
        Orders.OrderStatus orderStatus,
        List<PaymentType> paymentType,
        List<OrderItem> orderItem,
        Restaurant restaurant
) {
}
