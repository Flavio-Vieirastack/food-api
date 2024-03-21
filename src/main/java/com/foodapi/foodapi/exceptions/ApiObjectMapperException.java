package com.foodapi.foodapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiObjectMapperException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ApiObjectMapperException(String message) {
        super(message);
    }
}
