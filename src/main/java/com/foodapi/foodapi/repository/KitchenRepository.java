package com.foodapi.foodapi.repository;

import com.foodapi.foodapi.model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KitchenRepository extends JpaRepository<Kitchen, Long> {
}
