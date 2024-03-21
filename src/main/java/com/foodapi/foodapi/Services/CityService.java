package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.ServiceCallsExceptionHandler;
import com.foodapi.foodapi.exceptions.BadRequestException;
import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import com.foodapi.foodapi.model.City;
import com.foodapi.foodapi.model.Restaurant;
import com.foodapi.foodapi.repository.CityRepository;
import com.foodapi.foodapi.repository.StateRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        var state = stateRepository.findById(city.getState().getId());
        if (state.isPresent()) {
            city.setState(state.get());
            return serviceCallsExceptionHandler.executeOrThrowErrorsWithReturn(() -> {
                return cityRepository.save(city);
            });
        }
        throw new BadRequestException("The body " + city + " is invalid");
    }

    @Transactional
    public City update(@NotNull City city, Long id) {
        var cityInDb = searchOrNotFound(id);
        var state = stateRepository.findById(city.getState().getId());
        if (state.isPresent()) {
            city.setState(state.get());
            return serviceCallsExceptionHandler.executeOrThrowErrorsWithReturn(() -> {
                var newCity = apiObjectMapper.modelToUpdatedModel(city, cityInDb);
                var updatedCity = cityRepository.save(city);
                cityRepository.flush();
                return updatedCity;
            });
        }
        throw new EntityNotFoundException("State with id: " + city.getState().getId() + " not found");
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

    private @NotNull City searchOrNotFound(Long id) {
        return cityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Resource not found")
        );
    }
}
