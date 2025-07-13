package com.singh.ecommerceapp.product.repository;

import com.singh.ecommerceapp.product.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
}
