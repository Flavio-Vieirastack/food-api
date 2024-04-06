package com.foodapi.foodapi.Services;
import com.foodapi.foodapi.DTO.restaurant.RestaurantOutputDTO;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.ServiceCallsExceptionHandler;
import com.foodapi.foodapi.core.utils.ObjectValidate;
import com.foodapi.foodapi.exceptions.exceptionClasses.EntityConflictException;
import com.foodapi.foodapi.exceptions.exceptionClasses.EntityNotFoundException;
import com.foodapi.foodapi.model.models.Restaurant;
import com.foodapi.foodapi.model.models.UserClient;
import com.foodapi.foodapi.repository.CityRepository;
import com.foodapi.foodapi.repository.KitchenRepository;
import com.foodapi.foodapi.repository.RestaurantRepository;
import com.foodapi.foodapi.repository.UserClientRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private UserClientRepository userClientRepository;

    @Autowired
    private ApiObjectMapper<Restaurant> apiObjectMapper;
    @Autowired
    private ApiObjectMapper<RestaurantOutputDTO> apiObjectMapperResume;

    @Autowired
    private ServiceCallsExceptionHandler serviceCallsExceptionHandler;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ObjectValidate objectValidate;

    public List<RestaurantOutputDTO> getAll() {
        return restaurantRepository.findAll().stream().map(
                (restaurant) -> restaurant.toShort(apiObjectMapperResume)
        ).collect(Collectors.toList());
    }

    public Restaurant getOne(Long id) {
        return searchOrNotFound(id);
    }

    public List<UserClient> getAllUsers(Long restaurantId) {
        return searchOrNotFound(restaurantId).getUsers();
    }

    @Transactional
    public void addUser(Long restaurantId, Long userId) {
        var userInDb = userClientRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        var restaurant = searchOrNotFound(restaurantId);
        for (UserClient user : restaurant.getUsers()) {
            if(user.equals(userInDb)){
                throw new EntityConflictException("Conflict");
            }
        }
        restaurant.addUser(userInDb);
    }

    @Transactional
    public void removeUser(Long restaurantId, Long userId) {
        var user = userClientRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        var restaurant = searchOrNotFound(restaurantId);
        restaurant.removeUser(user);
    }

    @Transactional
    public Restaurant save(@NotNull Restaurant restaurant) {
        var cityID = restaurant.getAddress().getCity().getId();
        var kitchenId = restaurant.getKitchen().getId();
        var city = cityRepository.findById(cityID).orElseThrow(
                () -> new EntityNotFoundException(
                        "City with id: " + cityID + " not found")
        );
        var kitchen = kitchenRepository.findById(kitchenId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Kitchen with id: " + kitchenId + " not found"));
            restaurant.setKitchen(kitchen);
            restaurant.getAddress().setCity(city);
            return restaurantRepository.save(restaurant);
    }

    @Transactional
    public Restaurant update(@NotNull Restaurant restaurant, Long id) {
        objectValidate.throwEmptyBodyException(restaurant);
        objectValidate.throwEmptyListException(restaurant.getPaymentTypes(), "Empty list of payment types");
        objectValidate.throwEmptyListException(restaurant.getProducts(), "Empty list of products");
        var restaurantInDB = searchOrNotFound(id);
        return serviceCallsExceptionHandler.executeOrThrowErrorsWithReturn(() -> {
                    var newRestaurant = apiObjectMapper.modelToUpdatedModel(
                            restaurant,
                            restaurantInDB
                    );
                    var kitchen = restaurant.getKitchen();
                    if (kitchen != null) {
                        var newKitchen = kitchenRepository
                                .findById(
                                        kitchen.getId()
                                ).orElseThrow(
                                        () -> new EntityNotFoundException(
                                                "The kitchen with id: " + kitchen.getId() +
                                                        " is not present"
                                        ));
                        newRestaurant.setKitchen(newKitchen);
                    }
                    var address = restaurant.getAddress();
                    if(address != null && address.getCity() != null) {
                        var cityID = address.getCity().getId();
                        var newCity =cityRepository.findById(cityID).orElseThrow(
                                () -> new EntityNotFoundException(
                                        "The City with id: " + cityID +
                                                " is not present"
                                )
                        );
                        newRestaurant.getAddress().setCity(newCity);
                    }
                    var updatedRestaurant = restaurantRepository.save(newRestaurant);
                    restaurantRepository.flush();
                    cityRepository.flush();
                    kitchenRepository.flush();
                    return  updatedRestaurant;
                }
        );
    }

    @Transactional
    public void delete(Long id) {
        searchOrNotFound(id);
        serviceCallsExceptionHandler.executeOrThrowErrors(
                () -> {
                    restaurantRepository.deleteById(id);
                    restaurantRepository.flush();
                });
    }
    @Transactional
    public void inactivateRestaurant(Long id) {
       searchOrNotFound(id).setActive(false);
       // Não tem necessidade de chamar o save pois como roda em uma transação
        //o spring já sabe que isso deve ir para o banco de dados
    }
    @Transactional
    public void activateRestaurant(Long id) {
       searchOrNotFound(id).setActive(true);
       // Não tem necessidade de chamar o save pois como roda em uma transação
        //o spring já sabe que isso deve ir para o banco de dados
    }

    @Transactional
    public void activateVariousRestaurants(List<Long> restaurantIds) {
        restaurantIds.forEach(this::activateRestaurant);
    }
    @Transactional
    public void inactivateVariousRestaurants(List<Long> restaurantIds) {
        restaurantIds.forEach(this::inactivateRestaurant);
    }

    @Transactional
    public void openRestaurant(Long id) {
        searchOrNotFound(id).setOpen(true);
    }
    @Transactional
    public void closeRestaurant(Long id) {
        searchOrNotFound(id).setOpen(false);
    }

    public @NotNull Restaurant searchOrNotFound(Long id) {
        return restaurantRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Resource not found")
        );
    }

}
