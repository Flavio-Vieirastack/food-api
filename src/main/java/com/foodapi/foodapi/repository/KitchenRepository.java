package com.foodapi.foodapi.repository;

import com.foodapi.foodapi.model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long> {
    List<Kitchen> findByNameContaining(String name);
}
