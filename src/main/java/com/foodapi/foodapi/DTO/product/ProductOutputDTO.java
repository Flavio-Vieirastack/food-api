package com.foodapi.foodapi.DTO.product;

import java.math.BigDecimal;

public record ProductOutputDTO (
        Long id,
        String name,
        String description,
        BigDecimal price,
        boolean active
) {

}
