package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.order.CreateOrderDTO;
import com.foodapi.foodapi.Services.OrderService;
import com.foodapi.foodapi.model.models.Orders;
import com.foodapi.foodapi.DTO.order.OrdersOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    OrderService orderService;
    @GetMapping
    public ResponseEntity<List<OrdersOutput>> findAll() {
        return ResponseEntity.ok(orderService.getAll());
    }
    @PostMapping
    public void create(@RequestBody CreateOrderDTO createOrderDTO) {
        orderService.create(createOrderDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<Orders> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOne(id));
    }
}
