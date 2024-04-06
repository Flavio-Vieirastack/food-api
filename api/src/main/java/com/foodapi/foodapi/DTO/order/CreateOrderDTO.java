package com.foodapi.foodapi.DTO.order;

import com.foodapi.foodapi.model.Embedded.Address;
import com.foodapi.foodapi.model.models.Orders;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * The type Create order dto.
 */
public record CreateOrderDTO(
        @NotNull
        BigDecimal subTotal,
        @NotNull
        BigDecimal deliveryTax,
        @NotNull
        BigDecimal totalValue,
        @NotNull
        Orders.OrderStatus orderStatus,
        @NotNull
        OffsetDateTime creationDate,
        @NotNull
        OffsetDateTime confirmationDate,
        @NotNull
        OffsetDateTime deliveryDate,
        @NotNull
        OffsetDateTime cancelDate,
        @NotNull
        Long restaurantId,
        @NotNull
        Long userId,
        @NotNull
        Long paymentTypeId,
        @NotNull
        Long cityId,
        @NotNull
        Address address,
        @NotNull
        @Size(min = 1)
        List<OrderItemInputDTO> orderItemInputDTO
) {
}
