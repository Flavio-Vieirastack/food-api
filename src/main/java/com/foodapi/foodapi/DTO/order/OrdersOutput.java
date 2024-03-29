package com.foodapi.foodapi.DTO.order;


import com.foodapi.foodapi.DTO.restaurant.RestaurantOutputDTO;
import com.foodapi.foodapi.DTO.userClient.UserClientOutputDTO;
import com.foodapi.foodapi.model.models.Orders;
import com.foodapi.foodapi.model.models.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrdersOutput(
        Long id,
        BigDecimal subTotal,
        BigDecimal deliveryTax,
        BigDecimal totalValue,
        LocalDateTime creationDate,
        Orders.OrderStatus orderStatus,
        List<PaymentType> paymentType,
        RestaurantOutputDTO restaurant,
        UserClientOutputDTO userClient //Tem que deixar com o mesmo nome do model
) {

}
