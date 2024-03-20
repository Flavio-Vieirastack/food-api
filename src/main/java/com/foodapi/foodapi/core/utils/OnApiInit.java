package com.foodapi.foodapi.core.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnApiInit {

    @Autowired
    private ApiObjectMapper<?> apiObjectMapper;
    @PostConstruct
    public void init() {
        apiObjectMapper.mapperConfig();
        System.out.println("Método init() foi chamado na inicialização do Spring.");
    }
}
