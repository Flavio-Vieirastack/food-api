package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.exceptions.exceptionClasses.EntityNotFoundException;
import com.foodapi.foodapi.model.models.Product;
import com.foodapi.foodapi.repository.ProductRepository;
import com.foodapi.foodapi.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApiObjectMapper<List<Product>> apiObjectMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Product> getByRestaurantId(Long restaurantId) {
        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id: " + restaurantId + " not found"));
        return restaurant.getProducts();
    } // parei na 12.14

    public Product getOneByProductAndRestaurantId(Long productId, Long restaurantId) {
        var restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new EntityNotFoundException("Restaurant with id: " + restaurantId + " not found")
        );
        return restaurant.getProducts().stream().filter(
                product -> product.getId().equals(productId)
        ).findFirst().orElseThrow(() -> new EntityNotFoundException("Not found"));
    }
}
