package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.model.State;
import com.foodapi.foodapi.repository.StateRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateService {
    @Autowired
    private StateRepository stateRepository;

    public List<State> getAll() {
        return stateRepository.findAll();
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
}
