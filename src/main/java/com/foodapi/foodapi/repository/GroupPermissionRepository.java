package com.foodapi.foodapi.repository;

import com.foodapi.foodapi.model.GroupPermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPermissionRepository extends JpaRepository<GroupPermissions, Long> {
}
