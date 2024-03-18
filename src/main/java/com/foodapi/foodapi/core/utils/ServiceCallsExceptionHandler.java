package com.foodapi.foodapi.core.utils;

import com.foodapi.foodapi.exceptions.EntityConflictException;
import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class ServiceCallsExceptionHandler {

    public void executeOrThrowErrors(@NotNull RepositoryFunction function) {
        try {
            function.execute();
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException(ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new EntityConflictException(ex.getMessage());
        }
    }
    @FunctionalInterface
    public interface RepositoryFunction {
        void execute();
    }
}
