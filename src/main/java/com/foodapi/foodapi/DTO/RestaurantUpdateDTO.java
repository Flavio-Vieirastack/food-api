package com.foodapi.foodapi.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestaurantUpdateDTO(
        String name,
        BigDecimal deliveryTax,
        Long kitchenId,
        String zipCode,
        String publicPlace,
        String number,
        String complement,
        String district,
        Long cityID) {
}
