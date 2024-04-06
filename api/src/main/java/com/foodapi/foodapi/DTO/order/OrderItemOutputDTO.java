package com.foodapi.foodapi.DTO.order;

import java.math.BigDecimal;

public record OrderItemOutputDTO(
        Long id,
        Integer quantity,
        BigDecimal totalPrice,
        String observation
) {


}
