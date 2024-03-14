package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.model.Kitchen;
import com.foodapi.foodapi.repository.KitchenRepository;
import lombok.Setter;
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
}
