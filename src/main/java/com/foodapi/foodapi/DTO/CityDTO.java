package com.foodapi.foodapi.DTO;

import jakarta.validation.constraints.NotBlank;

public record CityDTO(@NotBlank String name, @NotBlank Long stateID) {
}
