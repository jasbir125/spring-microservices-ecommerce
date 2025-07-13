package com.singh.ecommerceapp.product.util;

import com.singh.ecommerceapp.product.entity.Product;

public class SlugGenerator {

    public static String generateSlug(Product product) {
        // Convert the product name and category to lowercase and replace spaces with hyphens
        String category = product.getCategory().stream()
                .map(s -> s.getCategoryName().length() >= 2 ? s.getCategoryName().substring(0, 2).toUpperCase() : s.getCategoryName().toUpperCase())  // Get the category name from Category object
                .findFirst()
                .orElse("GEN")
                .substring(0, 2)
                .toUpperCase();

        String baseSlug = (product.getName() + "-" + category)
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // Remove special characters
                .replaceAll("\\s+", "-");       // Replace spaces with hyphens

        // Append the last 6 characters of the UUID to ensure uniqueness
        String uniqueIdentifier = product.getProductId().toString().substring(product.getProductId().toString().length() - 6);

        return baseSlug + "-" + uniqueIdentifier;
    }
}
