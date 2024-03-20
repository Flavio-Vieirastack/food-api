package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.KitchenDTO;
import com.foodapi.foodapi.Services.KitchenService;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.OptionalReturnUtils;
import com.foodapi.foodapi.model.Kitchen;
import jakarta.validation.Valid;
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
    public void save(@Valid @RequestBody KitchenDTO kitchenDTO) {
        kitchenService.save(toModel(kitchenDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> update(@RequestBody KitchenDTO kitchenDTO, @PathVariable Long id){
       var updatedKitchen = kitchenService.update(toModel(kitchenDTO), id);
        return optionalReturnUtils.getResponseOrBadRequestStatusForCreated(updatedKitchen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Kitchen> delete(@PathVariable Long id) {
            kitchenService.delete(id);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Kitchen>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(kitchenService.findByName(name));
    }

    private Kitchen toModel(KitchenDTO kitchenDTO) {

        return mapper.dtoToModel(kitchenDTO, Kitchen.class);
    }
}
