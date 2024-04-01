package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.DTO.order.*;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.HasDuplicatedItems;
import com.foodapi.foodapi.core.utils.ObjectValidate;
import com.foodapi.foodapi.exceptions.exceptionClasses.EntityConflictException;
import com.foodapi.foodapi.exceptions.exceptionClasses.EntityNotFoundException;
import com.foodapi.foodapi.model.models.OrderItem;
import com.foodapi.foodapi.model.models.Orders;
import com.foodapi.foodapi.repository.OrderRepository;
import com.foodapi.foodapi.specs.OrderSpecs;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectValidate objectValidate;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserClientService userClientService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ProductService productService;

    @Autowired
    private HasDuplicatedItems hasDuplicatedItems;

    @Autowired
    private ApiObjectMapper<Orders> apiObjectMapper;
    @Autowired
    private ApiObjectMapper<OrdersOutput> apiObjectMapperOutput;
    @Autowired
    private ApiObjectMapper<OrderFilter> apiObjectMapperFilter;

    public List<OrderFilter> filter() {
        var orders = orderRepository.findAll();
        return orders.stream().map(
                (order) -> order.filterOrder(apiObjectMapperFilter)
        ).collect(Collectors.toList());
    }

    public List<Orders> filterOrdersBy(OrderInputFilterDTO orderInputFilterDTO) {
        objectValidate.throwEmptyBodyException(orderInputFilterDTO);
        return orderRepository.findAll(
                OrderSpecs.filterBy(orderInputFilterDTO)
        );
    }

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
