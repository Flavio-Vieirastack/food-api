package com.foodapi.foodapi.DTO.order;

import jakarta.validation.constraints.NotNull;

public record OrderItemInputDTO(
        @NotNull
        Long productId,
        @NotNull
        Integer quantity,
        String observation
) {
}
