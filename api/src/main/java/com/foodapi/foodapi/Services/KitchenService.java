package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.ServiceCallsExceptionHandler;
import com.foodapi.foodapi.exceptions.exceptionClasses.EntityNotFoundException;
import com.foodapi.foodapi.model.models.Kitchen;
import com.foodapi.foodapi.repository.KitchenRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KitchenService {
    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private ServiceCallsExceptionHandler serviceCallsExceptionHandler;

    @Autowired
    private ApiObjectMapper<Kitchen> apiObjectMapper;

    public Page<Kitchen> getAll(Pageable pageable) {

        return kitchenRepository.findAll(pageable);
    }

    public Kitchen getOne(Long id) {
        return searchOrThrowError(id);
    }

    public List<Kitchen> findByName(String name) {
        return kitchenRepository.findByNameContaining(name);
    }

    @Transactional
    public void save(Kitchen kitchen) {
        serviceCallsExceptionHandler.executeOrThrowErrors(() -> kitchenRepository.save(kitchen));
    }

    @Transactional
    public Kitchen update(Kitchen kitchen, Long id) {
        var kitchenInDB = searchOrThrowError(id);
        return serviceCallsExceptionHandler.executeOrThrowErrorsWithReturn(() -> {
            var newKitchen = apiObjectMapper.modelToUpdatedModel(kitchen, kitchenInDB);
            var updatedKitchen = kitchenRepository.save(newKitchen);
            kitchenRepository.flush();
            return updatedKitchen;
        });
    }

    @Transactional
    public void delete(Long id) {
        searchOrThrowError(id);
        serviceCallsExceptionHandler.executeOrThrowErrors(
                () -> {
                    kitchenRepository.deleteById(id);
                    kitchenRepository.flush();
                });

    }

    private @NotNull Kitchen searchOrThrowError(Long id) {
        return kitchenRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Resource not found"));
    }
}
