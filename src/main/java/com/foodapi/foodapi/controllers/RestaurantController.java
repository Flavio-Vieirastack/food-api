package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.RestaurantDTO;
import com.foodapi.foodapi.DTO.RestaurantUpdateDTO;
import com.foodapi.foodapi.Services.RestaurantService;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.OptionalReturnUtils;
import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import com.foodapi.foodapi.model.Kitchen;
import com.foodapi.foodapi.model.Restaurant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public ResponseEntity<Restaurant> save(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        var restaurantModel = apiObjectMapper.dtoToModel(
                restaurantDTO, Restaurant.class);
        var kitchen = new Kitchen();
        kitchen.setId(restaurantDTO.kitchenID());
        restaurantModel.setKitchen(kitchen);
        var createdRestaurant = restaurantService.save(
                restaurantModel);
        return optionalReturnUtils.getResponseOrBadRequestStatusForCreated(
                createdRestaurant);

    }

    @PutMapping("{id}")
    public ResponseEntity<Restaurant> update(
            @RequestBody RestaurantUpdateDTO restaurantUpdateDTO,
            @PathVariable Long id) {
        var restaurantModel = apiObjectMapper.dtoToModel(
                restaurantUpdateDTO, Restaurant.class);
        if(restaurantUpdateDTO.kitchenId() != null) {
            var newKitchen = new Kitchen();
            newKitchen.setId(restaurantUpdateDTO.kitchenId());
            restaurantModel.setKitchen(newKitchen);
        }
        var restaurantUpdateResponse = restaurantService.update(
                restaurantModel, id);
            return optionalReturnUtils.getResponseOrBadRequestStatusForOk(restaurantUpdateResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Restaurant> delete(@PathVariable Long id) {
       restaurantService.delete(id);
       return ResponseEntity.ok().build();
    }
}
