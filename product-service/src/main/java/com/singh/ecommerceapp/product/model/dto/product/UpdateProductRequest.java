package com.singh.ecommerceapp.product.model.dto.product;

import com.singh.ecommerceapp.product.entity.Seller;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateProductRequest {
    private String reference;
    private String name;
    private String description;
    private BigDecimal price;
    private String brand;
    private String size;
    private Integer quantity;
    private String color;
    private List<String> categoryNames;  // List of category names
    private LocalDateTime created;
    private List<String> categoryIds;
    private Seller seller;
}