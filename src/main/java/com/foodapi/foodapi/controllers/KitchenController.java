package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.kitchen.KitchenDTO;
import com.foodapi.foodapi.Services.KitchenService;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.model.models.Kitchen;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<Kitchen>> getAll(Pageable pageable) {
        return ResponseEntity.ok(kitchenService.getAll(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Kitchen> findOne(@PathVariable Long id) {
        var kitchen = kitchenService.getOne(id);
        return ResponseEntity.ok(kitchen);
    }
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody KitchenDTO kitchenDTO) {
        kitchenService.save(toModel(kitchenDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> update(@RequestBody KitchenDTO kitchenDTO, @PathVariable Long id){
       var updatedKitchen = kitchenService.update(toModel(kitchenDTO), id);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedKitchen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
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
