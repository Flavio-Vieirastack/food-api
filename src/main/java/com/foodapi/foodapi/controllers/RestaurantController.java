package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.RestaurantDTO;
import com.foodapi.foodapi.DTO.RestaurantUpdateDTO;
import com.foodapi.foodapi.Services.RestaurantService;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.model.City;
import com.foodapi.foodapi.model.Embedded.Address;
import com.foodapi.foodapi.model.Kitchen;
import com.foodapi.foodapi.model.Restaurant;
import jakarta.validation.Valid;
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
    ApiObjectMapper<Restaurant> apiObjectMapper;
    @Autowired
    ApiObjectMapper<Address> apiObjectMapperAddress;

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
        var city = new City();
        city.setId(restaurantDTO.cityID());
        kitchen.setId(restaurantDTO.kitchenID());
        restaurantModel.setKitchen(kitchen);
        var address = apiObjectMapperAddress.dtoToModel(restaurantDTO, Address.class);
        address.setCity(city);
        restaurantModel.setAddress(address);
        var createdRestaurant = restaurantService.save(
                restaurantModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);

    }

    @PutMapping("{id}")
    public ResponseEntity<Restaurant> update(
            @RequestBody RestaurantUpdateDTO restaurantUpdateDTO,
            @PathVariable Long id) {
        var restaurantModel = apiObjectMapper.dtoToModel(
                restaurantUpdateDTO, Restaurant.class);
        if (restaurantUpdateDTO.kitchenId() != null) {
            var newKitchen = new Kitchen();
            newKitchen.setId(restaurantUpdateDTO.kitchenId());
            restaurantModel.setKitchen(newKitchen);
        }
        if(restaurantUpdateDTO.complement() != null ||
                restaurantUpdateDTO.zipCode() != null ||
                restaurantUpdateDTO.publicPlace() != null ||
                restaurantUpdateDTO.number() != null ||
                restaurantUpdateDTO.district() != null) {
            var newAddress = apiObjectMapperAddress.dtoToModel(
                    restaurantUpdateDTO, Address.class);
            restaurantModel.setAddress(newAddress);
            if (restaurantUpdateDTO.cityID() != null) {
                newAddress.setCity(new City());
                newAddress.getCity().setId(restaurantUpdateDTO.cityID());
            }
        }
        var restaurantUpdateResponse = restaurantService.update(
                restaurantModel, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantUpdateResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Restaurant> delete(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable Long id) {
        restaurantService.activateRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/inactivate/{id}")
    public ResponseEntity<?> inactivate(@PathVariable Long id) {
        restaurantService.inactivateRestaurant(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/open/{id}")
    public ResponseEntity<?> openRestaurant(@PathVariable Long id) {
        restaurantService.openRestaurant(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/close/{id}")
    public ResponseEntity<?> closeRestaurant(@PathVariable Long id) {
        restaurantService.closeRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
