package com.foodapi.foodapi.core.utils;

import com.foodapi.foodapi.exceptions.exceptionClasses.EntityConflictException;
import com.foodapi.foodapi.exceptions.exceptionClasses.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;


@Component
public class ServiceCallsExceptionHandler {
    //Funções com @Transactional precisam dar flush depois de executadas
    public void executeOrThrowErrors(@NotNull RepositoryFunction function) {
        try {
            function.execute();
            System.out.println("Executado");
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException(ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new EntityConflictException("Entity in use, impossible to delete");
        }
    }

    public <T> T executeOrThrowErrorsWithReturn(@NotNull RepositoryFunctionWithReturn<T> function) {
        try {
            System.out.println("Executado");
            return function.execute();
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException(ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new EntityConflictException("Entity in use, impossible to delete");
        }
    }

    @FunctionalInterface
    public interface RepositoryFunction {
        void execute();
    }

    @FunctionalInterface
    public interface RepositoryFunctionWithReturn<T> {
        T execute();
    }
}
