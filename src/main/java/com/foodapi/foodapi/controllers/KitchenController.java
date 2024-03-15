package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.KitchenDTO;
import com.foodapi.foodapi.Services.KitchenService;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.OptionalReturnUtils;
import com.foodapi.foodapi.model.Kitchen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/kitchens")
public class KitchenController {
    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private ApiObjectMapper<Kitchen> mapper;

    @Autowired
    private OptionalReturnUtils<Kitchen> optionalReturnUtils;

    @GetMapping
    public ResponseEntity<List<Kitchen>> getAll() {

        return ResponseEntity.ok(kitchenService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Kitchen> findOne(@PathVariable Long id) {
        var kitchen = kitchenService.getOne(id);
        return optionalReturnUtils.getResponseOrNotFoundStatusWithNoContent(kitchen);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody KitchenDTO kitchenDTO) {
        kitchenService.save(toModel(kitchenDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> update(@RequestBody KitchenDTO kitchenDTO, @PathVariable Long id){
       var updatedKitchen = kitchenService.update(toModel(kitchenDTO), id);
        return optionalReturnUtils.getResponseOrNotFoundStatusWithNoContent(updatedKitchen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Kitchen> delete(@PathVariable Long id) {
        try {
            var deletedKitchen = kitchenService.delete(id);
            return optionalReturnUtils.getResponseOrNotFoundStatusWithNoContent(deletedKitchen);
        } catch(DataIntegrityViolationException ex) {
           return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    private Kitchen toModel(KitchenDTO kitchenDTO) {

        return mapper.dtoToModel(kitchenDTO, Kitchen.class);
    }
}
