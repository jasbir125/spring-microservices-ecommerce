package com.singh.ecommerceapp.product.entity;

import lombok.Data;

@Data
public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}