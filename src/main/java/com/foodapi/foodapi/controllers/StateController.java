package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.StateDTO;
import com.foodapi.foodapi.Services.StateService;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.model.State;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {
    @Autowired
    private StateService stateService;
    @Autowired
    private ApiObjectMapper<State> apiObjectMapper;

    @GetMapping
    public ResponseEntity<List<State>> getAll() {
        return ResponseEntity.ok(stateService.getAll());
    }

    @PostMapping
    public ResponseEntity<State> save(@Valid @RequestBody StateDTO stateDTO) {
        var result = stateService.save(
                apiObjectMapper.dtoToModel(stateDTO, State.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @GetMapping("{id}")
    public ResponseEntity<State> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(stateService.getOne(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<State> update(@RequestBody StateDTO stateDTO, @PathVariable Long id) {
        var newState = apiObjectMapper.dtoToModel(stateDTO, State.class);
        var updatedState = stateService.update(
                newState, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedState);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<State> delete(@PathVariable Long id) {
        stateService.delete(id);
        return ResponseEntity.ok().build();
    }
}
