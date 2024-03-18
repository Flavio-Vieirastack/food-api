package com.foodapi.foodapi.exceptions.exceptionBody;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ExceptionBody {
    private LocalDateTime date;
    private String message;
}
