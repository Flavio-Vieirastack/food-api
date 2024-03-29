package com.foodapi.foodapi.model.models;

import com.foodapi.foodapi.DTO.userClient.UserClientOutputDTO;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.interfaces.ToShort;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class UserClient implements ToShort<UserClientOutputDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToMany
    @JoinTable(name = "user_group_tb",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<GroupPermissions> groupPermissions;

    public void removeGroup(GroupPermissions groupPermissions) {
        getGroupPermissions().remove(groupPermissions);
    }

    public void addGroupPermission(GroupPermissions groupPermissions) {
        getGroupPermissions().add(groupPermissions);
    }

    @Override
    public UserClientOutputDTO toShort(ApiObjectMapper<UserClientOutputDTO> apiObjectMapper) {
        return apiObjectMapper.convert(this, UserClientOutputDTO.class);
    }
}

