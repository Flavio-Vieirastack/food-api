package com.foodapi.foodapi.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
public record RestaurantDTO(
        @NotBlank
        String name,
        @NotNull
        @DecimalMin("0")
        BigDecimal deliveryTax,
        @NotNull
        Long kitchenID
) {
}
