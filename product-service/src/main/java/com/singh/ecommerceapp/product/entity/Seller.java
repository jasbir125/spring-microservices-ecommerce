package com.singh.ecommerceapp.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "sellers")
public class Seller {
    private String sellerId;
    private String name;
    private String email;
    private String phone;
    private Address address;
    private double rating;
    private int totalProducts;
}

