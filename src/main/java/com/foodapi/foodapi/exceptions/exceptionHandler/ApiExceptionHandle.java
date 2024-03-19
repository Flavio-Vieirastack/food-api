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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandle extends ResponseEntityExceptionHandler {
    // Essa extensão serve para capturar todos os erros lançados pelo spring
    // E você pode dar override nesse metodo a baixo

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NotNull Exception ex, Object body, @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode statusCode, @NotNull WebRequest request
    ) {
        if (body == null) {
            body = buildExceptionBody(ex.getMessage(), ex, statusCode).build();
        } else if (body instanceof String) {
            body = buildExceptionBody((String) body, ex, statusCode).build();
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFound(
            @NotNull EntityNotFoundException ex, WebRequest request) {
        var exceptionBody = buildExceptionBody(
                ex.getMessage(), ex, HttpStatus.NOT_FOUND).build();
        return handleExceptionInternal(
                ex, exceptionBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(
            @NotNull BadRequestException ex, WebRequest request) {
        var exceptionBody = buildExceptionBody(
                ex.getMessage(), ex, HttpStatus.BAD_REQUEST).build();
        return handleExceptionInternal(
                ex, exceptionBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request
        );
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<?> conflict(
            @NotNull EntityConflictException ex, WebRequest request) {
        var exceptionBody = buildExceptionBody(
                ex.getMessage(), ex, HttpStatus.CONFLICT).build();
        return handleExceptionInternal(
                ex, exceptionBody, new HttpHeaders(), HttpStatus.CONFLICT, request
        );
    }

    private ExceptionBody.@NotNull ExceptionBodyBuilder buildExceptionBody(
            @NotNull String message,
            @NotNull Exception ex,
            @NotNull HttpStatusCode statusCode) {
        String uriBodyMessage = message.replace(" ", "-");
        var exceptionBody = ExceptionBody.builder();
        if (ex.getCause() != null){
            exceptionBody
                    .date(LocalDateTime.now())
                    .title(message)
                    .details(ex.getCause().getMessage())
                    .status(statusCode.value())
                    .type(
                            "https://api-prod.com.br/" + uriBodyMessage.toLowerCase());
        } else {
            exceptionBody
                    .date(LocalDateTime.now())
                    .title(message)
                    .status(statusCode.value())
                    .type(
                            "https://api-prod.com.br/" + uriBodyMessage.toLowerCase());
        }

        return exceptionBody;
    }


}
