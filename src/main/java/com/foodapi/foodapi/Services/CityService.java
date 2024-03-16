package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.model.City;
import com.foodapi.foodapi.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> getAll() {
        return cityRepository.findAll();
    }

    public Optional<City> geOne(Long id) {
        return cityRepository.findById(id);
    }
    @Transactional
    public Optional<City> create(City city) {
        try {
            return Optional.of(cityRepository.save(city));
        } catch (Exception ex) {
            System.out.println(ex.getCause().toString());
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<City> update(City city, Long id) {
        var cityInDb = geOne(id);
        if (cityInDb.isPresent()) {
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
    public void delete(Long id) {
        var cityInDb = geOne(id);
        if (cityInDb.isPresent()) {
            try {
                cityRepository.deleteById(id);
            } catch (Exception ex) {
                System.out.println(ex.getCause().toString());
            }
        }
    }
}
