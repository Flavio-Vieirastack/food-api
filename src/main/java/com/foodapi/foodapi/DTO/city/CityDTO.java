package com.foodapi.foodapi.DTO.city;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CityDTO(@NotBlank String name, @NotNull Long stateID) {
}
