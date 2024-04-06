package com.foodapi.foodapi.DTO.state;

import jakarta.validation.constraints.NotBlank;

public record StateDTO(@NotBlank String name) {
}
