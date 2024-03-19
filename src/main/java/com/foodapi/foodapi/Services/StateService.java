package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.core.utils.ServiceCallsExceptionHandler;
import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import com.foodapi.foodapi.model.State;
import com.foodapi.foodapi.repository.StateRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateService {
    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private ServiceCallsExceptionHandler serviceCallsExceptionHandler;

    public List<State> getAll() {
        return stateRepository.findAll();
    }

    public Optional<State> getOne(Long id) {
        return searchOrThrowErrors(id);
    }

    @Transactional
    public Optional<State> save(State state) {
        try {
            return Optional.of(stateRepository.save(state));
        } catch (Exception ex) {
            System.out.println(ex.getCause().toString());
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<State> update(State state, Long id) {
        var stateInDb = searchOrThrowErrors(id);
        if (stateInDb.isPresent()) {
            try {
                BeanUtils.copyProperties(state, stateInDb.get(), "id");
                return Optional.of(stateRepository.save(stateInDb.get()));
            } catch (Exception ex) {
                System.out.println(ex.getCause().toString());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        var stateInDb = searchOrThrowErrors(id);
        if (stateInDb.isPresent()) {
            serviceCallsExceptionHandler.executeOrThrowErrors(
                    () -> stateRepository.deleteById(id)
            );
        }
    }

    private @NotNull Optional<State> searchOrThrowErrors(Long id) {
        var result = stateRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Resource not found"));
        return Optional.of(result);
    }
}
