package com.foodapi.foodapi.model.Embedded;

import com.foodapi.foodapi.model.City;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class RestaurantAddress {
    @Column(name = "address_zip_code")
    private String zipCode;
    @Column(name = "address_public_place")
    private String publicPlace;
    @Column(name = "address_number")
    private String number;
    @Column(name = "address_complement")
    private String complement;
    @Column(name = "address_district")
    private String district;
    @JoinColumn(name = "address_city")
    @ManyToOne
    private City city;

}
