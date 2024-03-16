package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.model.Restaurant;
import com.foodapi.foodapi.repository.KitchenRepository;
import com.foodapi.foodapi.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRepository kitchenRepository;

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> getOne(Long id) {
        return restaurantRepository.findById(id);
    }

    @Transactional
    public Optional<Restaurant> save(Restaurant restaurant) {
        var kitchenId = restaurant.getKitchen().getId();
        var kitchen = kitchenRepository.findById(kitchenId);
        if (kitchen.isPresent()) {
            restaurant.setKitchen(kitchen.get());
            try {
                var restaurantCreated = restaurantRepository.save(restaurant);
                return Optional.of(restaurantCreated);
            } catch (Exception ex) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Restaurant> update(Restaurant restaurant, Long id) {
        var restaurantInDB = getOne(id);
        if (restaurantInDB.isPresent()) {
            try {
                copyPropertiesIfNull(restaurant, restaurantInDB.get());
                return Optional.of(restaurantRepository.save(restaurantInDB.get()));
            } catch (Exception ex) {
                // Adicionar erro
                System.out.println(ex.getCause().toString());
            }
        }
        return Optional.empty();
    }
    @Transactional
    public void delete(Long id) {
        try {
            restaurantRepository.deleteById(id);
        } catch (Exception ex) {
            // Adicionar erro
            System.out.println(ex.getCause().toString());
        }
    }
    // Remover quando houver melhor forma de tratar
    private void copyPropertiesIfNull(Restaurant restaurant, Restaurant restaurantInDB) {
        if (restaurant.getDeliveryTax() != null) {
            BeanUtils.copyProperties(
                    restaurant, restaurantInDB, "id", "kitchen", "name");
        } else if (restaurant.getName() != null) {
            BeanUtils.copyProperties(
                    restaurant, restaurantInDB, "id", "kitchen", "deliveryTax");

        } else {
            BeanUtils.copyProperties(
                    restaurant, restaurantInDB, "id", "kitchen");

        }

    }
}
