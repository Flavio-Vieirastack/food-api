package com.foodapi.foodapi.core.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OptionalReturnUtils<T> {
    public ResponseEntity<T> getResponseOrNotFoundStatus(Optional<T> optionalValue) {
        return optionalValue.map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build());
    }public ResponseEntity<T> getResponseOrBadRequestStatus(Optional<T> optionalValue) {
        return optionalValue.map(value -> ResponseEntity.status(
                HttpStatus.CREATED).body(value
        )).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
