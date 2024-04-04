package com.foodapi.foodapi.core.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { FileContentTypeValidator.class})
public @interface FileContentType {
    String message() default "Invalid content type, must be Jpg or PNG";

    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};

    String[] allowed();
}
