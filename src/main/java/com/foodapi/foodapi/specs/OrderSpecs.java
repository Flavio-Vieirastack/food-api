package com.foodapi.foodapi.specs;

import com.foodapi.foodapi.DTO.order.OrderInputFilterDTO;
import com.foodapi.foodapi.model.models.Orders;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class OrderSpecs {
    public static Specification<Orders> filterBy(OrderInputFilterDTO orderInputFilterDTO) {
        return (root, query, criteriaBuilder) -> {
            root.fetch("userClient");// serve para evitar que o spring faça vários selects
            root.fetch("restaurant").fetch("kitchen");// serve para evitar que o spring faça vários selects
            var predicates = new ArrayList<Predicate>();
            if(orderInputFilterDTO.clientId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("userClient").get("id"),
                                orderInputFilterDTO.clientId())
                );
            } else if(orderInputFilterDTO.restaurantId() != null) {
                predicates.add(criteriaBuilder.equal(
                                root.get("restaurant").get("id"),
                        orderInputFilterDTO.restaurantId())
                );
            } else if(orderInputFilterDTO.initialCreationDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("creationDate"),
                        orderInputFilterDTO.initialCreationDate()
                ));
            } else if(orderInputFilterDTO.endCreationDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("creationDate"),
                        orderInputFilterDTO.endCreationDate()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }
}
