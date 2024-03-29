package com.foodapi.foodapi.DTO.groupPermission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

/**
 * The type Group permission dto.
 */
public record GroupPermissionDTO(
        @NotBlank
        String name,
        @NotEmpty
        Set<Long> permissionsId
) {
}
