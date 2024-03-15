package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.DTO.KitchenDTO;
import com.foodapi.foodapi.model.Kitchen;
import com.foodapi.foodapi.repository.KitchenRepository;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class KitchenService {
    @Autowired
    private KitchenRepository kitchenRepository;

   public List<Kitchen> getAll() {
        return kitchenRepository.findAll();
    }
   public Optional<Kitchen> getOne(Long id) {
       return kitchenRepository.findById(id);
    }
    @Transactional
    public void save(Kitchen kitchen){
       try {
           kitchenRepository.save(kitchen);
       } catch (Exception ex) {
           System.out.println(ex.getCause().toString());
       }
    }@Transactional
    public Optional<Kitchen> update(Kitchen kitchen, Long id){
       var kitchenInDB = getOne(id);
       if(kitchenInDB.isPresent()){
           try {
               BeanUtils.copyProperties(kitchen, kitchenInDB.get(), "id");
              return Optional.of(kitchenRepository.save(kitchenInDB.get()));
           } catch (Exception ex) {
               System.out.println(ex.getCause().toString());
           }
       }
       return Optional.empty();
       // Adicionar erro
    }

    @Transactional
    public  Optional<Kitchen> delete(Long id) {
       var kitchen = kitchenRepository.findById(id);
       if (kitchen.isPresent()){
           kitchenRepository.deleteById(id);
       }
       return Optional.empty();
    }
}
