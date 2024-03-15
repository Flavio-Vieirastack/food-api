package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.RestaurantDTO;
import com.foodapi.foodapi.Services.RestaurantService;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.OptionalReturnUtils;
import com.foodapi.foodapi.model.Kitchen;
import com.foodapi.foodapi.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    OptionalReturnUtils<Restaurant> optionalReturnUtils;

    @Autowired
    ApiObjectMapper<Restaurant> apiObjectMapper;

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAll() {
        return ResponseEntity.ok(restaurantService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Restaurant> getOne(@PathVariable Long id) {
        var restaurant = restaurantService.getOne(id);
        return optionalReturnUtils.getResponseOrNotFoundStatus(restaurant);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> save(@RequestBody RestaurantDTO restaurantDTO) {
        var restaurantModel = apiObjectMapper.dtoToModel(
                restaurantDTO, Restaurant.class);
        var kitchen = new Kitchen();
        kitchen.setId(restaurantDTO.kitchenID());
        restaurantModel.setKitchen(kitchen);
        var createdRestaurant = restaurantService.save(
                restaurantModel);
        return optionalReturnUtils.getResponseOrBadRequestStatus(createdRestaurant);

    }
}
