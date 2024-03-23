package com.foodapi.foodapi.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

public record GroupPermissionDTO(
        @NotBlank
        String name,
        @NotEmpty
        Set<Long> permissionsId
) {
}
