package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.KitchenDTO;
import com.foodapi.foodapi.Services.KitchenService;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.model.Kitchen;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public ResponseEntity<List<Kitchen>> getAll() {
        return ResponseEntity.ok(kitchenService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Kitchen> findOne(@PathVariable Long id) {
        var kitchen = kitchenService.getOne(id);
        return kitchen.map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build());
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody KitchenDTO kitchenDTO) {
        kitchenService.save(toModel(kitchenDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> update(@RequestBody KitchenDTO kitchenDTO, @PathVariable Long id){
       var updatedKitchen = kitchenService.update(toModel(kitchenDTO), id);
        return updatedKitchen.map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/${id}")
    public ResponseEntity<Kitchen> delete(@PathVariable Long id) {
        var deletedKitchen = kitchenService.delete(id);
        return deletedKitchen.map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build());
    }

    private Kitchen toModel(KitchenDTO kitchenDTO) {
        return mapper.dtoToModel(kitchenDTO, Kitchen.class);
    }
}
