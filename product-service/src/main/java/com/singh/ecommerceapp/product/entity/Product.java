package com.singh.ecommerceapp.product.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "products")
public class Product extends
        BaseEntity{

    @Id
    private UUID productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String brand;
    private String size;
    private String color;
    private String currency;
    private String imageUrl;
    private String sku;
    private String slug;
    // Embedded documents
    private List<Tag> tags;
    private Seller seller;
    private List<Category> category;
}