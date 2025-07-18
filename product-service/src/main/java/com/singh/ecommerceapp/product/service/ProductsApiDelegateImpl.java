package com.singh.ecommerceapp.product.service;


import com.singh.ecommerceapp.product.api.ProductsApiDelegate;
import com.singh.ecommerceapp.product.api.model.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsApiDelegateImpl implements ProductsApiDelegate {

    @Override
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ProductsApiDelegate.super.getAllProducts();
    }
}
