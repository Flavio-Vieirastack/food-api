package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.DTO.UserClientDTO;
import com.foodapi.foodapi.DTO.UserClientUpdateDTO;
import com.foodapi.foodapi.core.utils.CreateAndUpdateEntityHelper;
import com.foodapi.foodapi.exceptions.EntityConflictException;
import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import com.foodapi.foodapi.model.GroupPermissions;
import com.foodapi.foodapi.model.UserClient;
import com.foodapi.foodapi.repository.GroupPermissionRepository;
import com.foodapi.foodapi.repository.UserClientRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserClientService {
    @Autowired
    private UserClientRepository userClientRepository;

    @Autowired
    private GroupPermissionRepository groupPermissionRepository;


    @Autowired
    CreateAndUpdateEntityHelper<UserClientUpdateDTO, UserClient, GroupPermissions, UserClientDTO> createAndUpdateEntityHelper;

    public List<UserClient> getAll() {
        return userClientRepository.findAll();
    }

    public UserClient getOne(Long id) {
        return findOrFail(id);
    }

    public List<GroupPermissions> getUserGroups(Long userId) {
        var user = findOrFail(userId);
        return user.getGroupPermissions();
    }

    @PostConstruct
    private void init() {
        createAndUpdateEntityHelper.setRepository(userClientRepository);
        createAndUpdateEntityHelper.setListRepository(groupPermissionRepository);
    }
    @Transactional
    public void create(UserClientDTO userClientDTO) {
        var hasUser = userClientRepository.findByEmail(userClientDTO.email());
        if(hasUser.isPresent()) {
            throw new EntityConflictException("User exists with this email");
        }
        createAndUpdateEntityHelper.create(userClientDTO, UserClient.class);
        userClientRepository.flush();
    }

    @Transactional
    public void removeGroup(Long userId, Long groupId) {
        var group = groupPermissionRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        var user = findOrFail(userId);
        user.removeGroup(group);
    }
    @Transactional
    public void addGroup(Long userId, Long groupId) {
        var group = groupPermissionRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        var user = findOrFail(userId);
        for (GroupPermissions groupPermissions : user.getGroupPermissions()) {
            if(groupPermissions.equals(group)) {
                throw new EntityConflictException("Conflict");
            }
        }
        user.addGroupPermission(group);
    }

    @Transactional
    public UserClient update(UserClientUpdateDTO userClientUpdateDTO, Long id) {
        if(userClientUpdateDTO.email() != null) {
            var hasClientWithEmail = userClientRepository.findByEmail(
                    userClientUpdateDTO.email()
            );
            if(hasClientWithEmail.isPresent()){
                throw new EntityConflictException("User exists with this email");
            }
        }
        if(userClientUpdateDTO.permissionsId() != null) {
            var result =
                    createAndUpdateEntityHelper.updateWithList(
                            userClientUpdateDTO,
                            id,
                            UserClient.class,
                            userClientUpdateDTO.permissionsId().stream().toList()
                    );
            var updatedClient = result.getUpdatedObject();
            updatedClient.setGroupPermissions(result.getListOfObjects());
            return updatedClient;
        }
        var result =
                createAndUpdateEntityHelper.updateWithList(
                        userClientUpdateDTO,
                        id,
                        UserClient.class,
                        null
                );
        var updatedClient = result.getUpdatedObject();
        updatedClient.setGroupPermissions(result.getListOfObjects());
        return updatedClient;
    }

    private UserClient findOrFail(Long id) {
        return userClientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id: " + id + " not found"));
    }
}
