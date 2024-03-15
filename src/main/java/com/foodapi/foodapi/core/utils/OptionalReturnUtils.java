package com.foodapi.foodapi.core.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OptionalReturnUtils<T> {
    public ResponseEntity<T> getResponseOrNotFoundStatus(Optional<T> optionalValue) {
        return optionalValue.map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build());
    }
}
