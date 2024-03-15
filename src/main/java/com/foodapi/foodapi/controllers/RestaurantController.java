package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.Services.RestaurantService;
import com.foodapi.foodapi.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAll() {
        return ResponseEntity.ok(restaurantService.getAll());
    }@GetMapping("{id}")
    public ResponseEntity<Restaurant> getOne(@PathVariable Long id) {
        var restaurant = restaurantService.getOne(id);
        return restaurant.map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build());
    }
}
