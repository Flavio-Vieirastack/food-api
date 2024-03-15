package com.foodapi.foodapi.core.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OptionalReturnUtils<T> {
    public ResponseEntity<T> getResponseOrNotFoundStatusWithNoContent(Optional<T> optionalValue) {
        return optionalValue.map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build());
    }public ResponseEntity<T> getResponseOrBadRequestStatusForCreated(Optional<T> optionalValue) {
        return optionalValue.map(value -> ResponseEntity.status(
                HttpStatus.CREATED).body(value
        )).orElseGet(() -> ResponseEntity.badRequest().build());
    }public ResponseEntity<T> getResponseOrBadRequestStatusForOk(Optional<T> optionalValue) {
        return optionalValue.map(value -> ResponseEntity.status(
                HttpStatus.OK).body(value
        )).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
