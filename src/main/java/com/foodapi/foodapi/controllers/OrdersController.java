package com.foodapi.foodapi.controllers;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.foodapi.foodapi.DTO.order.CreateOrderDTO;
import com.foodapi.foodapi.Services.OrderService;
import com.foodapi.foodapi.model.models.Orders;
import com.foodapi.foodapi.DTO.order.OrdersOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    OrderService orderService;

    @GetMapping("/filter")
    public MappingJacksonValue filter(
            @RequestParam String fields
    ) {
        //Esse metodo serve para buscar pedidos passando os campos que deseja retornar
        //Isso é feito com o @JsonFilter anotado no dto ou model
        //Aula 13.02
        //Ver a aula 13.03 que e ensinado a usar o squiggly para fazer isso em todos os-
        //endpoints
        var orderFilter = orderService.filter();
        var wrapper = new MappingJacksonValue(orderFilter);
        var filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("orderFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept(
                        fields.split(",")
                ));
        wrapper.setFilters(filterProvider);
        return wrapper;
    }

    @GetMapping
    public ResponseEntity<List<OrdersOutput>> findAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping
    public void create(@RequestBody CreateOrderDTO createOrderDTO) {
        orderService.create(createOrderDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<Orders> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOne(id));
    }
}
