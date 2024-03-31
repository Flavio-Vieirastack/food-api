package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.DTO.order.CreateOrderDTO;
import com.foodapi.foodapi.DTO.order.OrderItemInputDTO;
import com.foodapi.foodapi.DTO.order.OrdersOutput;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.HasDuplicatedItems;
import com.foodapi.foodapi.exceptions.exceptionClasses.EntityConflictException;
import com.foodapi.foodapi.exceptions.exceptionClasses.EntityNotFoundException;
import com.foodapi.foodapi.model.models.OrderItem;
import com.foodapi.foodapi.model.models.Orders;
import com.foodapi.foodapi.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserClientService userClientService;

    @Autowired
    PaymentTypeService paymentTypeService;

    @Autowired
    CityService cityService;

    @Autowired
    ProductService productService;

    @Autowired
    HasDuplicatedItems hasDuplicatedItems;

    @Autowired
    ApiObjectMapper<Orders> apiObjectMapper;
    @Autowired
    ApiObjectMapper<OrdersOutput> apiObjectMapperOutput;

    public List<OrdersOutput> getAll() {
        List<OrdersOutput> ordersOutputs = new ArrayList<>();
        var result = orderRepository.findAll();
        if (!result.isEmpty()) {
            for (Orders orders : result) {
                var newOrders = orders.toShort(apiObjectMapperOutput);
                ordersOutputs.add(newOrders);
            }
        }
        return ordersOutputs;
    }

    public Orders findOne(Long id) {
        return findOrFail(id);
    }

    @Transactional
    public void create(CreateOrderDTO createOrderDTO) {
        hasDuplicatedItems.hasDuplicates(createOrderDTO.orderItemInputDTO());
        var city = cityService.searchOrNotFound(createOrderDTO.cityId());
        var restaurant = restaurantService.searchOrNotFound(
                createOrderDTO.restaurantId());
        var user = userClientService.findOrFail(createOrderDTO.userId());
        var paymentType = paymentTypeService.findOrFail(createOrderDTO.paymentTypeId());
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemInputDTO orderItemInputDTO : createOrderDTO.orderItemInputDTO()) {
            var orderItem = new OrderItem();
            var product = productService.findOrFail(orderItemInputDTO.productId());
            if(!restaurant.getProducts().contains(product)) {
                throw new EntityConflictException("The restaurant not contains this product");
            }
            orderItem.addProduct(product);
            orderItem.setUnitaryPrice(product.getPrice());
            orderItem.totalPrice();
            orderItem.setQuantity(orderItemInputDTO.quantity());
            orderItem.setObservation(orderItemInputDTO.observation());
            orderItems.add(orderItem);
        }
        var newOrder = apiObjectMapper.dtoToModel(createOrderDTO, Orders.class);
        newOrder.setOrderItem(orderItems);
        newOrder.setRestaurant(restaurant);
        newOrder.setUserClient(user); //Adicionar depois via token
        if(!restaurant.getPaymentTypes().contains(paymentType)) {
            throw new EntityConflictException("The restaurant not contains this payment type");
        }
        newOrder.addPaymentType(paymentType);
        newOrder.getAddress().setCity(city);
        orderRepository.save(newOrder);
    }

    public Orders findOrFail(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Not found")
        );
    }
}
