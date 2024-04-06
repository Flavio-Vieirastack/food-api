package com.foodapi.foodapi.experiments.interfacesForServiceAndController;

import com.foodapi.foodapi.core.utils.ApiObjectMapper;

public interface ResumedModel<T> {
    T resume(ApiObjectMapper<T> apiObjectMapper);
}
