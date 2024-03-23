package com.foodapi.foodapi.core.utils;

import com.foodapi.foodapi.exceptions.EmptyUpdateBodyException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
@Component
public class UpdateObjectValidate {

    public void throwEmptyBodyException(Object target) {
        var result = Arrays.stream(target.getClass()
                        .getDeclaredFields())
                .peek(f -> f.setAccessible(true))
                .map(f -> getFieldValue(f, target))
                .allMatch(Objects::isNull);
        if(result) {
            throw new EmptyUpdateBodyException("The body is empty");
        }
    }

    public void throwEmptyListException(List<?> list, String message) {
        if(list != null && list.isEmpty()) {
            throw new EmptyUpdateBodyException(message);
        }
    }
    private Object getFieldValue(Field field, Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
