package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.Services.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-status/{id}")
public class OrderStatusController {
    @Autowired
    OrderStatusService orderStatusService;

    @PutMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable Long id) {
        orderStatusService.confirm(id);
    }
    @PutMapping("/delivered")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delivered(@PathVariable Long id) {
        orderStatusService.delivered(id);
    }
    @PutMapping("/cancelled")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelled(@PathVariable Long id) {
        orderStatusService.cancel(id);
    }
}
