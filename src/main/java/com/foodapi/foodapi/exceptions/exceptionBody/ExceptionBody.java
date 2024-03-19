package com.foodapi.foodapi.exceptions.exceptionBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ExceptionBody {
    private Integer status;
    private String title;
    private String details;
    private String type;
    private LocalDateTime date;
}
