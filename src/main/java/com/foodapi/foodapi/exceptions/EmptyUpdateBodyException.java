package com.foodapi.foodapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyUpdateBodyException extends  RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EmptyUpdateBodyException(String message) {
        super(message);
    }
}
