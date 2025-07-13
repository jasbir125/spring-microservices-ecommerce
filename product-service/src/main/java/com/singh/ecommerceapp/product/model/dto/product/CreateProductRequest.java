package com.singh.ecommerceapp.product.model.dto.product;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @Min(value = 0, message = "Quantity must be zero or greater")
    private Integer quantity;

    @Size(max = 100, message = "Brand name can't exceed 100 characters")
    private String brand;

    @Size(max = 50, message = "Size can't exceed 50 characters")
    private String size;

    @Size(max = 50, message = "Color can't exceed 50 characters")
    private String color;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter uppercase code (e.g., USD, INR)")
    private String currency;

    @Size(max = 255, message = "Image URL is too long")
    @Pattern(regexp = "^(http|https)://.*", message = "Image URL must be a valid URL")
    private String imageUrl;

    @Size(max = 10, message = "You can assign up to 10 tags")
    private List<@NotBlank(message = "Tag cannot be blank") String> tags;

    @Size(min = 1, message = "At least one category is required")
    private List<@NotBlank(message = "Category name cannot be blank") String> category;
}