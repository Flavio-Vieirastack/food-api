package com.foodapi.foodapi.DTO;

import com.foodapi.foodapi.model.City;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
        Long kitchenID,
        @NotBlank
        String zipCode,
        @NotBlank
        String publicPlace,
        @NotBlank
        String number,
        String complement,
        @NotBlank
        String district,
        @NotNull
        Long cityID
        ) {
}
