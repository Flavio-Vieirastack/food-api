package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.model.City;
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

    public List<City> getAll() {
        return cityRepository.findAll();
    }

    public Optional<City> geOne(Long id) {
        return cityRepository.findById(id);
    }
    @Transactional
    public Optional<City> create(@NotNull City city) {
        var state = stateRepository.findById(city.getState().getId());
        if(state.isPresent()) {
            city.setState(state.get());
            try {
                return Optional.of(cityRepository.save(city));
            } catch (Exception ex) {
                System.out.println(ex.getCause().toString());
            }
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<City> update(@NotNull City city, Long id) {
        var cityInDb = geOne(id);
        var state = stateRepository.findById(city.getState().getId());
        if (cityInDb.isPresent() && state.isPresent()) {
            city.setState(state.get());
            try {
                BeanUtils.copyProperties(city, cityInDb, "id");
                return Optional.of(cityRepository.save(city));
            } catch (Exception ex) {
                System.out.println(ex.getCause().toString());
            }
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<City> delete(Long id) {
        var cityInDb = geOne(id);
        if (cityInDb.isPresent()) {
            try {
                cityRepository.deleteById(id);
                return cityInDb;
            } catch (Exception ex) {
                System.out.println(ex.getCause().toString());
            }
        }
        return Optional.empty();
    }
}
