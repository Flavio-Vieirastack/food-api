package com.foodapi.foodapi.DTO.paymentType;

import jakarta.validation.constraints.NotBlank;

public record PaymentTypeDTO(@NotBlank String description) {
}
