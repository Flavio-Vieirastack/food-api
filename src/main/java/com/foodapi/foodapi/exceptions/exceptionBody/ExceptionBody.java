package com.foodapi.foodapi.exceptions.exceptionBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ExceptionBody {
    private Integer status;
    private String title;
    private String details;
    private String type;
    private LocalDateTime date;
    private List<FieldsErrors> fieldsErrors;
    @Getter
    @Builder
    public static class FieldsErrors{
        private String name;
        private String message;
    }
}
