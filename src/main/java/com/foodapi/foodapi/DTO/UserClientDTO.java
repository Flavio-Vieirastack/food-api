package com.foodapi.foodapi.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserClientDTO(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @NotNull
        @NotEmpty
        Set<Long> permissionsId
) {
}
