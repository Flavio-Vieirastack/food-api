package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.CityDTO;
import com.foodapi.foodapi.Services.CityService;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.OptionalReturnUtils;
import com.foodapi.foodapi.model.City;
import com.foodapi.foodapi.model.State;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private OptionalReturnUtils<City> optionalReturnUtils;

    @GetMapping
    public ResponseEntity<List<City>> getAll() {
        return ResponseEntity.ok(cityService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<City> getOne(@PathVariable Long id) {
        var city = cityService.geOne(id);
        return optionalReturnUtils.getResponseOrNotFoundStatusWithNoContent(city);
    }

    @PostMapping
    public ResponseEntity<City> create(@RequestBody CityDTO cityDTO) {
        var createdCity = cityService.create(toModel(cityDTO));
        return optionalReturnUtils.getResponseOrBadRequestStatusForCreated(createdCity);
    }

    @PutMapping("{id}")
    public ResponseEntity<City> update(
            @RequestBody CityDTO cityDTO, @PathVariable Long id) {
        var updatedCity = cityService.update(toModel(cityDTO), id);
        return optionalReturnUtils.getResponseOrNotFoundStatusWithNoContent(updatedCity);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<City> delete(@PathVariable Long id) {
        var deletedCity = cityService.delete(id);
        return optionalReturnUtils.getResponseOrNotFoundStatusWithNoContent(deletedCity);
    }

    private City toModel(CityDTO cityDTO) {
        var cityModel = apiObjectMapper.dtoToModel(cityDTO, City.class);
        var state = new State();
        state.setId(cityDTO.stateID());
        cityModel.setState(state);
        return cityModel;
    }
}