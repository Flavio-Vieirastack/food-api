package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.photo.ProductPhotoDTO;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurant/{restaurantId}/product/{productID}/photo")
public class ProductPhotoController {
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updatePhoto(
            @PathVariable Long restaurantId, @PathVariable Long productID,
            @Valid ProductPhotoDTO productPhotoDTO) {
        var fileName = UUID.randomUUID() + productPhotoDTO.file().getOriginalFilename();
        var path = Path.of(
                "/Users/flaviovieira/Documents/Estudo/spring/food-api/src/main/resources/files"
                , fileName);
        try {
            productPhotoDTO.file().transferTo(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Por padrão o spring só deixa enviar arquivos de até 1mb
        // e a requisição é até 10mb
        // isso é alterado no aplication.properties
        // exemplo spring.servlet.multipart.max-file-size=500KB
        // e para mudar o tamanho total da requisição spring.servlet.multipart.max-request-size=1MB
        // essa validação de tamanho pode ser feito com o bean validation criando uma validação customizada
        //Como está na pasta core/validations
    }
}
