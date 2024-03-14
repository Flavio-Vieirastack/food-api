package com.foodapi.foodapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.java.Log;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private State state;
}
