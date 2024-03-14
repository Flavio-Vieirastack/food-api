package com.foodapi.foodapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Kitchen {
    @Id()
    private Long id;
    private  String name;
}
