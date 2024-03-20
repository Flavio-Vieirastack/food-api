package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.ServiceCallsExceptionHandler;
import com.foodapi.foodapi.exceptions.BadRequestException;
import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import com.foodapi.foodapi.model.Restaurant;
import com.foodapi.foodapi.repository.KitchenRepository;
import com.foodapi.foodapi.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private ApiObjectMapper<Restaurant> apiObjectMapper;

    @Autowired
    private ServiceCallsExceptionHandler serviceCallsExceptionHandler;


    public List<Restaurant> getAll() {

        return restaurantRepository.findAll();
    }

    public Restaurant getOne(Long id) {
        return searchOrNotFound(id);
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
    public Restaurant update(@NotNull Restaurant restaurant, Long id) {
        var restaurantInDB = searchOrNotFound(id);
        try {
            var newRestaurant = apiObjectMapper.modelToUpdatedModel(
                    restaurant,
                    restaurantInDB
            );
            var kitchen = restaurant.getKitchen();
            if (kitchen != null) {
                var newKitchen = kitchenRepository.findById(kitchen.getId());
                newKitchen.ifPresent(newRestaurant::setKitchen);
            }
            return newRestaurant;
        } catch (Exception ex) {
            throw new BadRequestException("One or more fields are not compatible");
        }
    }

    public void delete(Long id) {
        searchOrNotFound(id);
        serviceCallsExceptionHandler.executeOrThrowErrors(
                () -> restaurantRepository.deleteById(id));
    }

    private @NotNull Restaurant searchOrNotFound(Long id) {
        return restaurantRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Resource not found")
        );
    }
}
