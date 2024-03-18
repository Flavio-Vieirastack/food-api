package com.foodapi.foodapi.exceptions.exceptionHandler;

import com.foodapi.foodapi.exceptions.BadRequestException;
import com.foodapi.foodapi.exceptions.EntityConflictException;
import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import com.foodapi.foodapi.exceptions.exceptionBody.ExceptionBody;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFound(@NotNull EntityNotFoundException ex) {
        var exceptionBody = buildExceptionBody(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionBody);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(@NotNull BadRequestException ex) {
        var exceptionBody = buildExceptionBody(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionBody);
    }
    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<?> badRequest(@NotNull EntityConflictException ex) {
        var exceptionBody = buildExceptionBody(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionBody);
    }

    private ExceptionBody.@NotNull ExceptionBodyBuilder buildExceptionBody(String message) {
        var exceptionBody = ExceptionBody.builder();
        exceptionBody.date(LocalDateTime.now()).message(message);
        return exceptionBody;
    }
}
