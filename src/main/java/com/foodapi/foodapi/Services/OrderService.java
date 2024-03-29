package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.DTO.order.CreateOrderDTO;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.exceptions.exceptionClasses.EntityNotFoundException;
import com.foodapi.foodapi.model.models.OrderItem;
import com.foodapi.foodapi.model.models.Orders;
import com.foodapi.foodapi.DTO.order.OrdersOutput;
import com.foodapi.foodapi.repository.OrderItemsRepository;
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
    OrderItemsRepository orderItemsRepository;

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
        var restaurant = restaurantService.searchOrNotFound(
                createOrderDTO.restaurantId());
        var user = userClientService.findOrFail(createOrderDTO.userId());

        var paymentType = paymentTypeService.findOrFail(createOrderDTO.paymentTypeId());

        List<OrderItem> orderItems = new ArrayList<>();

        for (Long orderItemID : createOrderDTO.ordersItemsId()) {
            var orderItem = orderItemsRepository.findById(orderItemID).orElseThrow(
                    () -> new EntityNotFoundException("Not found")
            );
            orderItems.add(orderItem);
        }
        var newOrder = apiObjectMapper.dtoToModel(createOrderDTO, Orders.class);
        newOrder.setOrderItem(orderItems);
        newOrder.setRestaurant(restaurant);
        newOrder.setUserClient(user);
        newOrder.addPaymentType(paymentType);
        orderRepository.save(newOrder);
        orderRepository.flush();
    }

    private Orders findOrFail(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Not found")
        );
    }
}
