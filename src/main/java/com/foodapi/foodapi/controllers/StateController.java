package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.StateDTO;
import com.foodapi.foodapi.Services.StateService;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.OptionalReturnUtils;
import com.foodapi.foodapi.model.State;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private OptionalReturnUtils<State> optionalReturnUtils;

    @GetMapping
    public ResponseEntity<List<State>> getAll() {
        return ResponseEntity.ok(stateService.getAll());
    }

    @PostMapping
    public ResponseEntity<State> save(@Valid @RequestBody StateDTO stateDTO) {
        var createdState = stateService.save(
                apiObjectMapper.dtoToModel(stateDTO, State.class));
        return optionalReturnUtils.getResponseOrBadRequestStatusForCreated(
                createdState);
    }

    @GetMapping("{id}")
    public ResponseEntity<State> getOne(@PathVariable Long id) {
        var state = stateService.getOne(id);
        return optionalReturnUtils.getResponseOrNotFoundStatusWithNoContent(state);
    }

    @PutMapping("{id}")
    public ResponseEntity<State> update(@RequestBody StateDTO stateDTO, @PathVariable Long id) {
        var newState = apiObjectMapper.dtoToModel(stateDTO, State.class);
        var updatedState = stateService.update(
                newState, id);
        return optionalReturnUtils.getResponseOrBadRequestStatusForCreated(updatedState);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<State> delete(@PathVariable Long id) {
        stateService.delete(id);
        return ResponseEntity.ok().build();
    }
}
