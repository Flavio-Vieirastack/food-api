package com.foodapi.foodapi.exceptions.exceptionHandler;

import com.foodapi.foodapi.exceptions.BadRequestException;
import com.foodapi.foodapi.exceptions.EntityConflictException;
import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import com.foodapi.foodapi.exceptions.exceptionBody.ExceptionBody;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    // Essa extensão serve para capturar todos os erros lançados pelo spring
    // E você pode dar override nesse metodo a baixo

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NotNull Exception ex, Object body, @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode statusCode, @NotNull WebRequest request
    ) {
        if (body == null) {
            body = buildExceptionBody(ex.getMessage()).build();
        } else if(body instanceof String) {
            body = buildExceptionBody((String) body).build();
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFound(
            @NotNull EntityNotFoundException ex, HttpHeaders headers,
            HttpStatusCode statusCode, WebRequest request) {
        var exceptionBody = buildExceptionBody(ex.getMessage()).build();
        return handleExceptionInternal(ex, exceptionBody, headers, statusCode, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(
            @NotNull BadRequestException ex, HttpHeaders headers,
            HttpStatusCode statusCode, WebRequest request) {
        var exceptionBody = buildExceptionBody(ex.getMessage()).build();
         return handleExceptionInternal(ex, exceptionBody, headers, statusCode, request);
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<?> badRequest(
            @NotNull EntityConflictException ex, HttpHeaders headers,
            HttpStatusCode statusCode, WebRequest request) {
        var exceptionBody = buildExceptionBody(ex.getMessage()).build();
         return handleExceptionInternal(ex, exceptionBody, headers, statusCode, request);
    }

    private ExceptionBody.@NotNull ExceptionBodyBuilder buildExceptionBody(String message) {
        var exceptionBody = ExceptionBody.builder();
        exceptionBody.date(LocalDateTime.now()).message(message);
        return exceptionBody;
    }


}
