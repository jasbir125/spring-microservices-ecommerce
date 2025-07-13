package com.singh.ecommerceapp.product.model.dto.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductResponse {
    private UUID productId;
    //private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String brand;
    private String size;
    private String color;
    private String currency;
    private String imageUrl;

    // Just names, not full objects
    private List<String> tags;
    private List<String> category;
}