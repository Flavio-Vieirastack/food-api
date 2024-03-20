package com.foodapi.foodapi.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CityDTO(@NotBlank String name, @NotNull Long stateID) {
}
