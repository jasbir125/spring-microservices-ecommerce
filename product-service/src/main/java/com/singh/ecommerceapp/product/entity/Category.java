package com.singh.ecommerceapp.product.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a product category stored in MongoDB.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor                          // No-arg constructor
@AllArgsConstructor                         // Constructor with all fields
@Builder
@Document(collection = "category")
public class Category extends BaseEntity {

	@Id
	private String categoryId;

	@NotBlank(message = "Category name is required")
	private String categoryName;

	@NotBlank(message = "Category description is required")
	@Size(max = 200, message = "Description cannot be more than 200 characters")
	private String categoryDescription;

}