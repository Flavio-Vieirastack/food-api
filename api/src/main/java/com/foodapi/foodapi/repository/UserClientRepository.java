package com.foodapi.foodapi.repository;

import com.foodapi.foodapi.model.models.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserClientRepository extends JpaRepository<UserClient, Long> {
    Optional<UserClient> findByEmail(String email);
}
