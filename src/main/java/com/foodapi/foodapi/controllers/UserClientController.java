package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.UserClientDTO;
import com.foodapi.foodapi.DTO.UserClientUpdateDTO;
import com.foodapi.foodapi.Services.UserClientService;
import com.foodapi.foodapi.model.UserClient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user-client")
public class UserClientController {
    @Autowired
    UserClientService userClientService;
    @GetMapping
    public List<UserClient> findAll() {
        return userClientService.getAll();
    }
    @GetMapping("{id}")
    public UserClient findOne(@PathVariable Long id) {
        return userClientService.getOne(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserClientDTO userClientDTO) {
         userClientService.create(userClientDTO);
         return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UserClient> update(@PathVariable Long id, @RequestBody UserClientUpdateDTO userClientUpdateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userClientService.update(userClientUpdateDTO, id));
    }
}
