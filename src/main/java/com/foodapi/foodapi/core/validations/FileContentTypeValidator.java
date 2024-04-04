package com.foodapi.foodapi.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {
    private String[] contentType;
    @Override
    public void initialize(FileContentType constraintAnnotation) {
        contentType = constraintAnnotation.allowed();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return Arrays.asList(contentType).contains(value.getContentType());
    }
}
