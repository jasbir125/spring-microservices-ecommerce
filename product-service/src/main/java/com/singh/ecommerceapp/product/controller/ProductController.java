package com.singh.ecommerceapp.product.controller;

import com.singh.ecommerceapp.product.mapper.ProductMapper;
import com.singh.ecommerceapp.product.model.dto.product.UpdateProductRequest;
import com.singh.ecommerceapp.product.model.dto.product.ProductResponse;
import com.singh.ecommerceapp.product.model.dto.product.CreateProductRequest;
import com.singh.ecommerceapp.product.entity.Product;
import com.singh.ecommerceapp.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProductsByProductIds(@RequestParam List<UUID> productIds) {
        List<ProductResponse> productDtos = productService.getProductsByIds(productIds);
        return new ResponseEntity<>(productDtos, OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductByProductId(@PathVariable UUID productId) {
        Product product = productService.validateAndGetProductById(productId);
        ProductResponse productResponse = productMapper.toProductResponse(product);
        return new ResponseEntity<>(productResponse, OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid  @RequestBody CreateProductRequest createProductRequest) {
        ProductResponse response = productService.createProduct(createProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID productId, @RequestBody UpdateProductRequest productRequest) {
        ProductResponse response = productService.updateProduct(productId, productRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/sku/{skuCode}")
    public ResponseEntity<ProductResponse> getProductBySku(@PathVariable("skuCode") String skuCode) {
        ProductResponse productResponse = productService.findBySku(skuCode);
        return ResponseEntity.ok(productResponse);

    }


}

