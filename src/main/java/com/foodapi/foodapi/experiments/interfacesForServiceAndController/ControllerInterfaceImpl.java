package com.foodapi.foodapi.experiments.interfacesForServiceAndController;

import com.foodapi.foodapi.DTO.restaurant.RestaurantDTO;
import com.foodapi.foodapi.DTO.restaurant.RestaurantUpdateDTO;
import com.foodapi.foodapi.model.models.Restaurant;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ControllerInterfaceImpl implements
        ControllerInterface<Restaurant, RestaurantDTO, RestaurantUpdateDTO>{
    @Override
    public ResponseEntity<List<Restaurant>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<Restaurant> findOne(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Restaurant> create(RestaurantDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<Restaurant> updateValue(RestaurantUpdateDTO dto, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return null;
    }
}
