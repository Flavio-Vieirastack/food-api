package com.foodapi.foodapi.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestaurantUpdateDTO(
        String name,
        BigDecimal deliveryTax,
        Long kitchenId) {
}
