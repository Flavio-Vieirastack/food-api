package com.foodapi.foodapi.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;
//Essa classe customiza os dados da paginação
@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {
    @Override
    public void serialize(Page<?> objects,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("content", objects.getContent());
        jsonGenerator.writeNumberField("size", objects.getSize());
        jsonGenerator.writeNumberField("totalElements", objects.getTotalElements());
        jsonGenerator.writeNumberField("totalPages", objects.getTotalPages());
        jsonGenerator.writeNumberField("number", objects.getNumber());
        jsonGenerator.writeEndObject();

    }
}
