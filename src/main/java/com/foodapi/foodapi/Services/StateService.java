package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.model.State;
import com.foodapi.foodapi.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {
    @Autowired
    private StateRepository stateRepository;

    public List<State> getAll() {
        return stateRepository.findAll();
    }
}
