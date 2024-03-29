package com.foodapi.foodapi.DTO.kitchen;

import jakarta.validation.constraints.NotBlank;

public record KitchenDTO(@NotBlank String name) {
}
