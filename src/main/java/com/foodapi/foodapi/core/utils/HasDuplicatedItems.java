package com.foodapi.foodapi.core.utils;

import com.foodapi.foodapi.exceptions.DuplicatedItemException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class HasDuplicatedItems {
    public void hasDuplicates(List<?> list) {
        Set<Object> set = new HashSet<>();
        for (Object element : list) {
            if (!set.add(element)) {
                throw new DuplicatedItemException("Has and duplicated item in your request");
            }
        }
    }
}
