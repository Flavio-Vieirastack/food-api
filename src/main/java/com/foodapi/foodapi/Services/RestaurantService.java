package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.model.Restaurant;
import com.foodapi.foodapi.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;

    public List<Restaurant> getAll(){
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> getOne(Long id) {
        return restaurantRepository.findById(id);
    }
}
