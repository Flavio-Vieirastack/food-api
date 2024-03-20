package com.foodapi.foodapi.DTO;

import java.math.BigDecimal;

public record RestaurantUpdateDTO(
        String name,
        BigDecimal deliveryTax,
        Long kitchenId) {
}
