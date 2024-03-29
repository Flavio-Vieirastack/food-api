package com.foodapi.foodapi.model.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class GroupPermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(name = "group_permissions_tb",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    public void removePermission(Permission permission) {
        getPermissions().remove(permission);
    }

    public void addPermission(Permission permission) {
        getPermissions().add(permission);
    }
}
