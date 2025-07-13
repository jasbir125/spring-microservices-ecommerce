package com.singh.ecommerceapp.product.util;

import org.springframework.stereotype.Component;

@Component
public class SkuGenerator {

    /**
     * Generates a unique SKU based on product attributes.
     *
     * @param category The product category (e.g., "electronics").
     * @param brand    The brand name (e.g., "Apple").
     * @param color    The product color (e.g., "Black").
     * @param size     The product size or variation (e.g., "128GB").
     * @return The generated SKU (e.g., "APP-ELE-BLK-128GB").
     */
    public String generateSku(String category, String brand, String color, String size) {
        // Format: BRAND-CATEGORY-COLOR-SIZE
        String formattedCategory = abbreviate(category, 3).toUpperCase();
        String formattedBrand = abbreviate(brand, 3).toUpperCase();
        String formattedColor = abbreviate(color, 3).toUpperCase();

        // Combine attributes
        return String.format("%s-%s-%s-%s", formattedBrand, formattedCategory, formattedColor, size);
    }

    /**
     * Abbreviates a string to a specified length.
     *
     * @param input  The string to abbreviate.
     * @param length The desired length.
     * @return The abbreviated string.
     */
    private String abbreviate(String input, int length) {
        if (input == null || input.isEmpty()) {
            return "UNK"; // Unknown placeholder
        }
        return input.length() <= length ? input : input.substring(0, length);
    }
}