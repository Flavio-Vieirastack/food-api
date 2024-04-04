package com.foodapi.foodapi.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {
    //Converte String de kb para mb ex: "500kb"
    private DataSize dataSize;
    public void initialize(FileSize constraintAnnotation) {
        dataSize = DataSize.parse(constraintAnnotation.max());
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || value.getSize() <= dataSize.toBytes();
    }
}
