package com.foodapi.foodapi.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.foodapi.foodapi.exceptions.ApiObjectMapperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.InternalParseException;
import org.springframework.stereotype.Component;

@Component
public class ApiObjectMapper<T> {
    @Autowired
    private ObjectMapper mapper;
    public void mapperConfig() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public T dtoToModel(Object dto, Class<T> model) {
        return mapper.convertValue(dto, model);
    }

    public T modelToUpdatedModel(Object model, T updatedModel) {
        try {
            return mapper.updateValue(updatedModel, model);
        } catch (JsonMappingException ex) {
            throw new ApiObjectMapperException(ex.getMessage());
        }
    }

}
