package com.foodapi.foodapi.DTO;

import jakarta.validation.constraints.NotBlank;

public record StateDTO(@NotBlank String name) {
}
