package com.foodapi.foodapi.repository;

import com.foodapi.foodapi.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
