package com.singh.ecommerceapp.product.service;

import com.singh.ecommerceapp.product.entity.Category;
import com.singh.ecommerceapp.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> mapCategories(Set<String> categoryNames) {
        return categoryNames.stream()
                .map(name -> categoryRepository.findByCategoryName(name)
                        .orElseGet(() -> categoryRepository.save(
                                Category.builder()
                                        .categoryName(name)
                                        .categoryId(UUID.randomUUID().toString())
                                        .categoryDescription("Auto-created category")
                                        .build()))
                )
                .collect(Collectors.toList());
    }

    public void deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public Optional<Category> getCategoryById(String id) {
        return categoryRepository.findById(id);
    }

    public Category updateCategory(String categoryId, Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (existingCategory.isPresent()) {
            Category updatedCategory = existingCategory.get();
            updatedCategory.setCategoryName(category.getCategoryName());
            updatedCategory.setCategoryDescription(category.getCategoryDescription());
            return categoryRepository.save(updatedCategory);
        } else {
            throw new RuntimeException("Category not found");
        }
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }



}
