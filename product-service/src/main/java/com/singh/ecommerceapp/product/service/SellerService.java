package com.singh.ecommerceapp.product.service;

import com.singh.ecommerceapp.product.entity.Seller;
import com.singh.ecommerceapp.product.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    public Optional<Seller> findSellerById(String sellerId) {
        return sellerRepository.findById(sellerId);
    }

    public void validateSellerExistence(Seller seller) {
        if (seller == null || findSellerById(seller.getSellerId()).isEmpty()) {
            throw new RuntimeException("Seller not found");
        }
    }
}
