package com.foodapi.foodapi.DTO.groupPermission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

public record GroupPermissionUpdateDTO(
        String name,
        Set<Long> permissionsId
) {
}
