package com.foodapi.foodapi.experiments.interfacesForServiceAndController;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface ControllerInterface<O,I,U> {
    ResponseEntity<List<O>> findAll();
    ResponseEntity<O> findOne(Long id);
    ResponseEntity<O> create( I dto);
    ResponseEntity<O> updateValue( U dto, Long id);
    ResponseEntity<?> delete(Long id);

    default O toModel(O model) {
        return  null;
    }
}
