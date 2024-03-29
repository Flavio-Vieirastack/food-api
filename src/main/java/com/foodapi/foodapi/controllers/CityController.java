package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.city.CityDTO;
import com.foodapi.foodapi.Services.CityService;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.model.models.City;
import com.foodapi.foodapi.model.models.State;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    @Autowired
    private CityService cityService;

    @Autowired
    private ApiObjectMapper<City> apiObjectMapper;


    @GetMapping
    public ResponseEntity<List<City>> getAll() {
        return ResponseEntity.ok(cityService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<City> getOne(@PathVariable Long id) {
        var city = cityService.geOne(id);
        return ResponseEntity.ok(city);
    }

    @PostMapping
    public ResponseEntity<City> create(@Valid @RequestBody CityDTO cityDTO) {
        var createdCity = cityService.create(toModel(cityDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }

    @PutMapping("{id}")
    public ResponseEntity<City> update(
            @RequestBody CityDTO cityDTO, @PathVariable Long id) {
        var updatedCity = cityService.update(toModel(cityDTO), id);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCity);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        cityService.delete(id);
        return ResponseEntity.ok().build();
    }

    private @NotNull City toModel(CityDTO cityDTO) {
        var cityModel = apiObjectMapper.dtoToModel(cityDTO, City.class);
        var state = new State();
        state.setId(cityDTO.stateID());
        cityModel.setState(state);
        return cityModel;
    }
}
