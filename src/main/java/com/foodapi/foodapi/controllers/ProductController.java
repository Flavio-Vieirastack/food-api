package com.foodapi.foodapi.controllers;

import com.foodapi.foodapi.Services.ProductService;
import com.foodapi.foodapi.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Product>> getByProductId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getByRestaurantId(id));
    }

    @GetMapping("/restaurant/{restaurantId}/product/{productId}")
    public ResponseEntity<Product> getOneByProductAndRestaurantId(@PathVariable Long restaurantId, @PathVariable Long productId) {
        return ResponseEntity.ok(productService.getOneByProductAndRestaurantId(productId, restaurantId));
    }
}
