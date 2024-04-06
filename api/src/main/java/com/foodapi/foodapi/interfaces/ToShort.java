package com.foodapi.foodapi.interfaces;

import com.foodapi.foodapi.core.utils.ApiObjectMapper;

public interface ToShort<T> {
    T toShort(ApiObjectMapper<T> apiObjectMapper);
}
