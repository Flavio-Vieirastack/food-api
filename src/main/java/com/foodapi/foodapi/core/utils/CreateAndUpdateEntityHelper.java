package com.foodapi.foodapi.core.utils;

import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Setter
public class CreateAndUpdateEntityHelper<I, O, L, C> {
    private JpaRepository<O, Long> repository;
    private JpaRepository<L, Long> listRepository;
    private JpaRepository<L, Long> nestedObjectRepository;
    @Autowired
    ApiObjectMapper<O> apiObjectMapper;

    @Autowired
    UpdateObjectValidate updateObjectValidate;

    @Autowired
    HasDuplicatedItems hasDuplicatedItems;

    public O update(I dto, Long id, Class<O> target) {
        updateObjectValidate.throwEmptyBodyException(dto);
        var objectInDb = findOrFail(id);
        var newObject = apiObjectMapper.dtoToModel(dto, target);
        var updatedObject = apiObjectMapper.modelToUpdatedModel(newObject, objectInDb);
        var updatedObjectInDb = repository.save(updatedObject);//No prpjeto final deixar o save para o service e ver a questão do flush
        repository.flush();
        return updatedObjectInDb;
    }

    public UpdatedOrCreatedObject<O,L> updateWithOneNestedObject(I dto, Long id, Long nestedObjectId) {
        updateObjectValidate.throwEmptyBodyException(dto);
        var objectInDb = findOrFail(id);
        var newObject = apiObjectMapper.modelToUpdatedModel(dto, objectInDb);
        var updatedObject = new UpdatedOrCreatedObject<O,L>();
        if(nestedObjectId != null) {
            var nestedObjectInDb = nestedObjectRepository.findById(nestedObjectId)
                    .orElseThrow(
                    () -> new EntityNotFoundException("Entity with id: " + nestedObjectId + " not found")
            );
            updatedObject.setNestedObject(nestedObjectInDb);
        }
        updatedObject.setUpdatedObject(newObject);
        return updatedObject;
    }

    public UpdatedOrCreatedObject<O,L> updateWithList(I dto, Long id, Class<O> target,
                                                      List<Long> ids) {
        updateObjectValidate.throwEmptyListException(ids, "List of ids are empty");
        updateObjectValidate.throwEmptyBodyException(dto);
        var objectInDb = findOrFail(id);
        var model = apiObjectMapper.dtoToModel(dto, target);
        var newObject = apiObjectMapper.modelToUpdatedModel(objectInDb, model);
        var updated = new UpdatedOrCreatedObject<O, L>();
        if (ids != null && !ids.isEmpty()) {
            hasDuplicatedItems.hasDuplicates(ids);
            Set<L> set = new HashSet<>();
            for (Long idData : ids) {
                var result = listRepository.findById(idData).orElseThrow(
                        () -> new EntityNotFoundException("Entity with id: " + idData + " not found")
                );
                set.add(result);
            }
            updated.setListOfObjects(set.stream().toList());
        } else {
            var listOfObjects = listRepository.findAll();
            updated.setListOfObjects(listOfObjects);
        }
        repository.save(newObject);//No prpjeto final deixar o save para o service e ver a questão do flush
        updated.setUpdatedObject(newObject);
        repository.flush();
        listRepository.flush();
        return updated;
    }

    public void create(C dto, Class<O> target) {
        var createdObject = apiObjectMapper.dtoToModel(dto, target);
        repository.save(createdObject);
        repository.flush();
    }
    public UpdatedOrCreatedObject<O,L> createWithList(C dto, Class<O> target, List<Long> ids) {
        hasDuplicatedItems.hasDuplicates(ids);
        Set<L> set = new HashSet<>();
        for (Long id : ids) {
           var result = listRepository.findById(id).orElseThrow(
                   () -> new EntityNotFoundException("Entity with id: + " + id + " not found")
           );
           set.add(result);
        }
        var updatedObject = apiObjectMapper.dtoToModel(dto, target);
        var createdObject = new UpdatedOrCreatedObject<O,L>();
        createdObject.setUpdatedObject(updatedObject);
        createdObject.setListOfObjects(set.stream().toList());
        listRepository.flush();
        return createdObject;
    }

    private O findOrFail(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity with id: " + id + " not found")
        );
    }
    @Getter
    @Setter
    public static class UpdatedOrCreatedObject<O, L> {
        private O updatedObject;
        private List<L> listOfObjects;
        private L nestedObject;
    }
}
