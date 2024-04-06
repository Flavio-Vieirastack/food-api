package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.DTO.groupPermission.GroupPermissionDTO;
import com.foodapi.foodapi.DTO.groupPermission.GroupPermissionUpdateDTO;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.CreateAndUpdateEntityHelper;
import com.foodapi.foodapi.core.utils.HasDuplicatedItems;
import com.foodapi.foodapi.core.utils.ServiceCallsExceptionHandler;
import com.foodapi.foodapi.exceptions.exceptionClasses.EmptyUpdateBodyException;
import com.foodapi.foodapi.exceptions.exceptionClasses.EntityNotFoundException;
import com.foodapi.foodapi.model.models.GroupPermissions;
import com.foodapi.foodapi.model.models.Permission;
import com.foodapi.foodapi.repository.GroupPermissionRepository;
import com.foodapi.foodapi.repository.PermissionRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GroupPermissionService {
    @Autowired
    private GroupPermissionRepository groupPermissionRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ApiObjectMapper<GroupPermissions> apiObjectMapper;

    @Autowired
    private ServiceCallsExceptionHandler serviceCallsExceptionHandler;

    @Autowired
    private HasDuplicatedItems hasDuplicatedItems;

    @Autowired
    private CreateAndUpdateEntityHelper<?, GroupPermissions, Permission, GroupPermissionDTO> createEntity;
    @Autowired
    private CreateAndUpdateEntityHelper<GroupPermissionUpdateDTO, GroupPermissions, Permission, ?> updateEntity;

    @PostConstruct
    private void init() {
        createEntity.setListRepository(permissionRepository);
    }
    public List<GroupPermissions> getAll() {
        return groupPermissionRepository.findAll();
    }

    public GroupPermissions getOne(Long id) {
        return findOrFail(id);
    }

    public List<Permission> getPermissionByGroupId(Long groupId) {
        var groupPermissions = findOrFail(groupId);
        return groupPermissions.getPermissions().stream().toList();
    }

    @Transactional
    public void create(GroupPermissionDTO groupPermissionDTO) {
//        hasDuplicatedItems.hasDuplicates(groupPermissionDTO.permissionsId().stream().toList());
//        var permissions = getAllPermissions(groupPermissionDTO.permissionsId());
//        var groupPermissions = apiObjectMapper.dtoToModel(
//                groupPermissionDTO, GroupPermissions.class);
//        groupPermissions.setPermissions(permissions);
//        groupPermissionRepository.save(groupPermissions);
//        groupPermissionRepository.flush();
        var result =
                createEntity.createWithList(
                        groupPermissionDTO,
                        GroupPermissions.class,
                        groupPermissionDTO.permissionsId().stream().toList()
                );
        var groupPermission = result.getUpdatedObject();
        groupPermission.setPermissions(new HashSet<>(result.getListOfObjects()));
        groupPermissionRepository.save(groupPermission);
        groupPermissionRepository.flush();
    }

    @Transactional
    public GroupPermissions update(GroupPermissionUpdateDTO groupPermissionUpdateDTO, Long id) {
        hasDuplicatedItems.hasDuplicates(groupPermissionUpdateDTO.permissionsId().stream().toList());
        verifyAllFields(groupPermissionUpdateDTO);
        var groupPermissionsInDb = findOrFail(id);
        var newGroupPermission = apiObjectMapper.dtoToModel(
                groupPermissionUpdateDTO, GroupPermissions.class);
        Set<Permission> permissions;
        var updatedPermissions =
                apiObjectMapper.modelToUpdatedModel(
                        newGroupPermission, groupPermissionsInDb);
        if (groupPermissionUpdateDTO.permissionsId() != null && !groupPermissionUpdateDTO.permissionsId().isEmpty()) {
            permissions = getAllPermissions(new HashSet<>(groupPermissionUpdateDTO.permissionsId()));
            updatedPermissions.setPermissions(permissions);
        }
        var createdGroupPermission = groupPermissionRepository.save(updatedPermissions);
        groupPermissionRepository.flush();
        return createdGroupPermission;
    }

    @Transactional
    public void delete(Long id) {
        findOrFail(id);
        serviceCallsExceptionHandler.executeOrThrowErrors(
                () -> {
                    groupPermissionRepository.deleteById(id);
                    groupPermissionRepository.flush();
                });
    }

    @Transactional
    public void addPermission(Long permissionId, Long groupId) {
        var permission = permissionRepository.findById(permissionId).orElseThrow(
                () -> new EntityNotFoundException("Not found")
        );
        var group = findOrFail(groupId);
        group.addPermission(permission);
    }
    @Transactional
    public void removePermission(Long permissionId, Long groupId) {
        var permission = permissionRepository.findById(permissionId).orElseThrow(
                () -> new EntityNotFoundException("Not found")
        );
        var group = findOrFail(groupId);
        group.removePermission(permission);
    }

    private Set<Permission> getAllPermissions(Set<Long> permissionsId) {
        Set<Permission> permissions = new HashSet<>();
        for (Long permissionId : permissionsId) {
            var permission = permissionRepository.findById(permissionId).orElseThrow(
                    () -> new EntityNotFoundException("The permission with id: " + permissionId + " not found")
            );
            permissions.add(permission);
        }
        return permissions;
    }

    private GroupPermissions findOrFail(Long id) {
        return groupPermissionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Permission with id: " + id + " not found")
        );
    }

    private void verifyAllFields(GroupPermissionUpdateDTO groupPermissionUpdateDTO) {
        if (groupPermissionUpdateDTO.name() == null && groupPermissionUpdateDTO.permissionsId() == null) {
            throw new EmptyUpdateBodyException("This body is empty");
        }
        if(groupPermissionUpdateDTO.permissionsId() != null && groupPermissionUpdateDTO.permissionsId().isEmpty()) {
            throw new EmptyUpdateBodyException("Permissions is empty");
        }
    }
}
