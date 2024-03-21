package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.core.utils.ApiObjectMapper;
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
    private ApiObjectMapper<State> apiObjectMapper;

    @Autowired
    private ServiceCallsExceptionHandler serviceCallsExceptionHandler;

    public List<State> getAll() {
        return stateRepository.findAll();
    }

    public State getOne(Long id) {
        return searchOrThrowErrors(id);
    }

    @Transactional
    public State save(State state) {
        return stateRepository.save(state);
    }

    @Transactional
    public State update(State state, Long id) {
        var stateInDb = searchOrThrowErrors(id);
        return serviceCallsExceptionHandler.executeOrThrowErrorsWithReturn(() -> {
            var updatedState = apiObjectMapper.modelToUpdatedModel(state, stateInDb);
            var result = stateRepository.save(updatedState);
            stateRepository.flush();
            //O flush serve para que essa função consiga capturar o erro
            //mesmo com a anotação @Transactional
            return result;
        });
    }

    @Transactional
    public void delete(Long id) {
        searchOrThrowErrors(id);
        serviceCallsExceptionHandler.executeOrThrowErrors(
                () -> {
                    stateRepository.deleteById(id);
                    //O flush serve para que essa função consiga capturar o erro
                    //mesmo com a anotação @Transactional
                    stateRepository.flush();
                }
        );
    }

    private @NotNull State searchOrThrowErrors(Long id) {
        return stateRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Resource not found"));
    }
}
