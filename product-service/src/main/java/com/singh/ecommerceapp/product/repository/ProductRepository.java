package com.singh.ecommerceapp.product.repository;


import com.singh.ecommerceapp.product.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends MongoRepository<Product, UUID> {
    Optional<Product> findByName(String name);

    Optional<Product> findBySku(String sku);

    List<Product> findByProductIdIn(List<UUID> ids);
}