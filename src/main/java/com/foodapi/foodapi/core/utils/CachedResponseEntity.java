package com.foodapi.foodapi.core.utils;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.TimeUnit;
public class CachedResponseEntity<T> extends ResponseEntity<T> {

    public CachedResponseEntity(MultiValueMap<String, String> headers, HttpStatusCode status) {
        super(headers, status);
    }

    static public <T> ResponseEntity<T> getCachedResponse(T body) {
        return ResponseEntity.ok().cacheControl(
                CacheControl.maxAge(10, TimeUnit.SECONDS)).body(body);
    }
}
