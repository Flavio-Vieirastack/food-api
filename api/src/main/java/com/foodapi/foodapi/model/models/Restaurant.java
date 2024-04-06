package com.foodapi.foodapi.model.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foodapi.foodapi.DTO.restaurant.RestaurantOutputDTO;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.interfaces.ToShort;
import com.foodapi.foodapi.model.Embedded.Address;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Restaurant implements ToShort<RestaurantOutputDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "delivery_tax", nullable = false)
    private BigDecimal deliveryTax;
    private boolean active;
    @ManyToOne
    private Kitchen kitchen; //Muitos restaurantes tem uma cozinha
    @ManyToMany
    @JoinTable(name = "restaurant_payment_type",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_type_id")
    )
    private List<PaymentType> paymentTypes = new ArrayList<>();
    @Embedded
    private Address address;
    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(columnDefinition = "datetime")
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "restaurant")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Product> products = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "restaurant_users",
            joinColumns = @JoinColumn(
            name = "restaurant_id"
    ),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserClient> users;
    private boolean isOpen;

    public void addUser(UserClient userClient) {
        getUsers().add(userClient);
    }
    public void removeUser(UserClient userClient) {
        getUsers().remove(userClient);
    }
    @Override
    public RestaurantOutputDTO toShort(ApiObjectMapper<RestaurantOutputDTO> apiObjectMapper) {
        return apiObjectMapper.convert(this, RestaurantOutputDTO.class);
    }
}
