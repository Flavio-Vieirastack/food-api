package com.foodapi.foodapi.core.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiObjectMapper<T> {
    @Autowired
    private ObjectMapper mapper;

    public T dtoToModel(Object dto, Class<T> model) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return  mapper.convertValue(dto, model);
    }

}
