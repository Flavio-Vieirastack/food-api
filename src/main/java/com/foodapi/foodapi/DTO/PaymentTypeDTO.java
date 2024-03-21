package com.foodapi.foodapi.DTO;

import jakarta.validation.constraints.NotBlank;

public record PaymentTypeDTO(@NotBlank String description) {
}
