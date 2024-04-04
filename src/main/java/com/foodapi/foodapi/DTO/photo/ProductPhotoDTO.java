package com.foodapi.foodapi.DTO.photo;

import com.foodapi.foodapi.core.validations.FileSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ProductPhotoDTO(
        @NotNull
        @FileSize(max = "500KB")
        MultipartFile file,
        @NotBlank
        String description
) {
}
