package com.foodapi.foodapi.DTO.order;

import java.time.LocalDateTime;

public record OrderInputFilterDTO(
        Long clientId,
        Long restaurantId,
        LocalDateTime initialCreationDate,
        LocalDateTime endCreationDate
) {
}
