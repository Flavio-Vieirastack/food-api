package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.core.utils.ServiceCallsExceptionHandler;
import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import com.foodapi.foodapi.model.Kitchen;
import com.foodapi.foodapi.repository.KitchenRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class KitchenService {
    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private ServiceCallsExceptionHandler serviceCallsExceptionHandler;

    public List<Kitchen> getAll() {
        return kitchenRepository.findAll();
    }

    public Optional<Kitchen> getOne(Long id) {
       return searchOrThrowError(id);
    }

    public List<Kitchen> findByName(String name) {

        return kitchenRepository.findByNameContaining(name);
    }

    @Transactional
    public void save(Kitchen kitchen) {
        try {
            kitchenRepository.save(kitchen);
        } catch (Exception ex) {
            System.out.println(ex.getCause().toString());
        }
    }

    @Transactional
    public Optional<Kitchen> update(Kitchen kitchen, Long id) {
        var kitchenInDB = searchOrThrowError(id);
        if (kitchenInDB.isPresent()) {
            try {
                BeanUtils.copyProperties(kitchen, kitchenInDB.get(), "id");
                return Optional.of(kitchenRepository.save(kitchenInDB.get()));
            } catch (Exception ex) {
                // Adicionar erro
                System.out.println(ex.getCause().toString());
            }
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        searchOrThrowError(id);
        serviceCallsExceptionHandler.executeOrThrowErrors(
                () -> kitchenRepository.deleteById(id));

    }

    private @NotNull Optional<Kitchen> searchOrThrowError(Long id) {
        var result = kitchenRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Resource not found"));
        return Optional.of(result);
    }
}
