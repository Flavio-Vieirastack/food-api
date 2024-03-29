package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.DTO.groupPermission.GroupPermissionDTO;
import com.foodapi.foodapi.DTO.groupPermission.GroupPermissionUpdateDTO;
import com.foodapi.foodapi.Services.GroupPermissionService;
import com.foodapi.foodapi.model.models.GroupPermissions;
import com.foodapi.foodapi.model.models.Permission;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/group-permission")
public class GroupPermissionController {
    @Autowired
    private GroupPermissionService groupPermissionService;
    @GetMapping
    public ResponseEntity<List<GroupPermissions>> findAll() {
        return ResponseEntity.ok(groupPermissionService.getAll());
    }
    @GetMapping("{id}")
    public ResponseEntity<GroupPermissions> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(groupPermissionService.getOne(id));
    }

    @GetMapping("/permissions/{id}")
    public ResponseEntity<List<Permission>> getPermissionsByGroup(@PathVariable Long id) {
        return ResponseEntity.ok(groupPermissionService.getPermissionByGroupId(id));
    }
    @PutMapping("/permissions/{permissionId}/group-permission/{groupPermissionId}")
    public ResponseEntity<?> addPermission(
            @PathVariable Long permissionId, @PathVariable Long groupPermissionId) {
        groupPermissionService.addPermission(permissionId, groupPermissionId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/permissions/{permissionId}/group-permission/{groupPermissionId}")
    public ResponseEntity<?> removePermission(
            @PathVariable Long permissionId, @PathVariable Long groupPermissionId) {
        groupPermissionService.removePermission(permissionId, groupPermissionId);
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody GroupPermissionDTO groupPermissionDTO) {
        groupPermissionService.create(groupPermissionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<GroupPermissions> update(
            @PathVariable Long id, @RequestBody GroupPermissionUpdateDTO groupPermissionUpdateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                groupPermissionService.update(groupPermissionUpdateDTO, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        groupPermissionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
