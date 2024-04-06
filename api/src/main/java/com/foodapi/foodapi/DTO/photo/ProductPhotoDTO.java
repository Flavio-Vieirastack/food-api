package com.foodapi.foodapi.DTO.photo;

import com.foodapi.foodapi.core.validations.FileContentType;
import com.foodapi.foodapi.core.validations.FileSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public record ProductPhotoDTO(
        @NotNull
        @FileSize(max = "500KB")
        @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
        MultipartFile file,
        @NotBlank
        String description
) {
}
