package com.foodapi.foodapi.DTO;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record RestaurantDTO(
        @NotBlank String name,
        @NotBlank BigDecimal deliveryTax,
        @NotBlank Long kitchenID
) {
}
