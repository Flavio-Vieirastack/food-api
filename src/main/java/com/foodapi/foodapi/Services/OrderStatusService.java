package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.exceptions.exceptionClasses.EntityConflictException;
import com.foodapi.foodapi.model.models.Orders;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderStatusService {
    @Autowired
    OrderService orderService;

    @Transactional
    public void confirm(Long orderId) {
       var order = changeStatus(
               orderId, Orders.OrderStatus.CONFIRMED, Orders.OrderStatus.CREATED);
       order.setConfirmationDate(LocalDateTime.now());
    }

    @Transactional
    public void delivered(Long orderId) {
       var order = changeStatus(
               orderId, Orders.OrderStatus.DELIVERED, Orders.OrderStatus.CONFIRMED);
       order.setDeliveryDate(LocalDateTime.now());
    }
    @Transactional
    public void cancel(Long orderId) {
        var order = orderService.findOrFail(orderId);
        if(!order.getOrderStatus().equals(Orders.OrderStatus.CONFIRMED) && order.getOrderStatus().equals(Orders.OrderStatus.DELIVERED)) {
            throw new EntityConflictException("Status is not confirmed or is delivered");
        }
        order.setOrderStatus(Orders.OrderStatus.CANCELED);
        order.setCancelDate(LocalDateTime.now());
    }

    private Orders changeStatus(Long orderId, Orders.OrderStatus newOrderStatus, Orders.OrderStatus statusToCompare) {
        var order = orderService.findOrFail(orderId);
        if (!order.getOrderStatus().equals(statusToCompare)) {
            throw new EntityConflictException("Order cannot be confirmed if is not created");
        }
        order.setOrderStatus(newOrderStatus);
        return order;
    }
}
