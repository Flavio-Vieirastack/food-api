package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.paymentType.PaymentTypeDTO;
import com.foodapi.foodapi.Services.PaymentTypeService;
import com.foodapi.foodapi.core.utils.CachedResponseEntity;
import com.foodapi.foodapi.model.models.PaymentType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-type")
public class PaymentTypeController {
    @Autowired
    PaymentTypeService paymentTypeService;
    @GetMapping
    public ResponseEntity<List<PaymentType>> getAll() {
        return CachedResponseEntity.getCachedResponse(paymentTypeService.getAll());
    }
    @GetMapping("{id}")
    public ResponseEntity<PaymentType> getOne(@PathVariable Long id) {
        return CachedResponseEntity.getCachedResponse(paymentTypeService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PaymentTypeDTO paymentTypeDTO) {
        paymentTypeService.create(paymentTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<PaymentType> update(
            @Valid @RequestBody PaymentTypeDTO paymentTypeDTO, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                paymentTypeService.update(paymentTypeDTO, id)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        paymentTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
