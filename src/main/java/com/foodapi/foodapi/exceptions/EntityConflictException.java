package com.foodapi.foodapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
@ResponseStatus(HttpStatus.CONFLICT)
public class EntityConflictException extends  RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public EntityConflictException(String message) {
        super(message);
    }
}
