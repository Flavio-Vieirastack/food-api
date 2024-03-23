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

    @PostConstruct
    private void init() {
        createAndUpdateEntityHelper.setRepository(userClientRepository);
        createAndUpdateEntityHelper.setListRepository(groupPermissionRepository);
    }
    @Transactional
    public void create(UserClientDTO userClientDTO) {
//        hasDuplicatedItems.hasDuplicates(userClientDTO.permissionsId().stream().toList());
//        var userClient = apiObjectMapper.dtoToModel(userClientDTO, UserClient.class);
//        var permissions = findOrFailPermissions(
//                userClientDTO.permissionsId());
//        userClient.setGroupPermissions(permissions.stream().toList());
//        userClientRepository.flush();
        var hasUser = userClientRepository.findByEmail(userClientDTO.email());
        if(hasUser != null) {
            throw new EntityConflictException("User exists with this email");
        }
        createAndUpdateEntityHelper.create(userClientDTO, UserClient.class);
    }

    @Transactional
    public UserClient update(UserClientUpdateDTO userClientUpdateDTO, Long id) {
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
