package com.foodapi.foodapi.DTO;

import java.math.BigDecimal;

public record RestaurantDTO(String name, BigDecimal deliveryTax, Long kitchenID) {
}
