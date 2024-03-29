package com.foodapi.foodapi.DTO.userClient;

import java.util.Set;

public record UserClientUpdateDTO(
        String name,
        String email,
        String password,
        Set<Long> permissionsId
) {
}
