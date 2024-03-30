package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.ServiceCallsExceptionHandler;
import com.foodapi.foodapi.exceptions.exceptionClasses.EntityNotFoundException;
import com.foodapi.foodapi.model.models.City;
import com.foodapi.foodapi.repository.CityRepository;
import com.foodapi.foodapi.repository.StateRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private ServiceCallsExceptionHandler serviceCallsExceptionHandler;

    @Autowired
    private ApiObjectMapper<City> apiObjectMapper;


    public List<City> getAll() {
        return cityRepository.findAll();
    }

    public City geOne(Long id) {
        return searchOrNotFound(id);
    }

    @Transactional
    public City create(@NotNull City city) {
        var state = stateRepository.findById(city.getState().getId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "The state with id: " + city.getState().getId() + " not found")
        );
        city.setState(state);
        return serviceCallsExceptionHandler.executeOrThrowErrorsWithReturn(() ->
                cityRepository.save(city)
        );
    }

    @Transactional
    public City update(@NotNull City city, Long id) {
        var cityInDb = searchOrNotFound(id);
        var state = stateRepository.findById(city.getState().getId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "State with id: " + city.getState().getId() + " not found")
        );
        city.setState(state);
        return serviceCallsExceptionHandler.executeOrThrowErrorsWithReturn(() -> {
            var newCity = apiObjectMapper.modelToUpdatedModel(city, cityInDb);
            var updatedCity = cityRepository.save(newCity);
            cityRepository.flush();
            return updatedCity;
        });
    }

    @Transactional
    public void delete(Long id) {
        searchOrNotFound(id);
        serviceCallsExceptionHandler.executeOrThrowErrors(
                () -> {
                    cityRepository.deleteById(id);
                    cityRepository.flush();
                });
    }

    public @NotNull City searchOrNotFound(Long id) {
        return cityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Resource not found")
        );
    }
}
