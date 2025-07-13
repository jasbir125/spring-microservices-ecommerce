package com.singh.ecommerceapp.product.repository;

import com.singh.ecommerceapp.product.entity.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SellerRepository extends MongoRepository<Seller, String> {
    Optional<Seller> findByName(String name);
}