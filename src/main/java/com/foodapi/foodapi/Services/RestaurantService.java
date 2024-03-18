package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.exceptions.EntityConflictException;
import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import com.foodapi.foodapi.model.Restaurant;
import com.foodapi.foodapi.repository.KitchenRepository;
import com.foodapi.foodapi.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
        searchOrNotFound(id);

        return restaurantRepository.findById(id);
    }

    @Transactional
    public Optional<Restaurant> save(@NotNull Restaurant restaurant) {
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
        searchOrNotFound(id);
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
        searchOrNotFound(id);
        try {
            restaurantRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException(ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new EntityConflictException(ex.getMessage());
        }
    }

    // Remover quando houver melhor forma de tratar
    private void copyPropertiesIfNull(@NotNull Restaurant restaurant, Restaurant restaurantInDB) {
        if (restaurant.getDeliveryTax() != null) {
            BeanUtils.copyProperties(
                    restaurant, restaurantInDB,
                    "id", "kitchen", "name", "paymentTypes",
                    "restaurantAddress", "createdAt");
        } else if (restaurant.getName() != null) {
            BeanUtils.copyProperties(
                    restaurant, restaurantInDB,
                    "id", "kitchen", "deliveryTax",
                    "paymentTypes", "restaurantAddress", "createdAt");

        } else {
            BeanUtils.copyProperties(
                    restaurant, restaurantInDB,
                    "id", "kitchen", "paymentTypes",
                    "restaurantAddress", "createdAt");

        }

    }

    private void searchOrNotFound(Long id) {
        restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Resource not found")
        );
    }
}
