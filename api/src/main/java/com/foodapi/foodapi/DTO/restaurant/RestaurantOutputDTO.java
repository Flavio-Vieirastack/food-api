package com.foodapi.foodapi.DTO.restaurant;

import com.foodapi.foodapi.model.models.Kitchen;
import com.foodapi.foodapi.model.models.PaymentType;

import java.math.BigDecimal;
import java.util.List;

public record RestaurantOutputDTO (
        Long id,
        String name,
        BigDecimal deliveryTax,
        boolean active,
        boolean isOpen,
        List<PaymentType>paymentTypes,
        Kitchen kitchen

) {


}
