package com.singh.ecommerceapp.product.controller;

import com.singh.ecommerceapp.product.mapper.IProductMapper;
import com.singh.ecommerceapp.product.model.dto.product.UpdateProductRequest;
import com.singh.ecommerceapp.product.model.dto.product.ProductResponse;
import com.singh.ecommerceapp.product.model.dto.product.CreateProductRequest;
import com.singh.ecommerceapp.product.entity.Product;
import com.singh.ecommerceapp.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Product Controller", description = "APIs for managing products")
public class ProductController {

    private final ProductService productService;
    private final IProductMapper productMapper;

    @Operation(summary = "Get Products by IDs", description = "Retrieve a list of products based on provided UUIDs")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved products")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProductsByProductIds(@RequestParam List<UUID> productIds) {
        List<ProductResponse> productDtos = productService.getProductsByIds(productIds);
        return new ResponseEntity<>(productDtos, OK);
    }

    @Operation(summary = "Get Product by ID", description = "Retrieve product details by product UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductByProductId(@PathVariable UUID productId) {
        Product product = productService.validateAndGetProductById(productId);
        ProductResponse productResponse = productMapper.toProductResponse(product);
        return new ResponseEntity<>(productResponse, OK);
    }

    @Operation(summary = "Create Product", description = "Create a new product with the provided data")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        ProductResponse response = productService.createProduct(createProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Delete Product", description = "Delete an existing product by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update Product", description = "Update an existing product by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID productId,
                                                         @RequestBody UpdateProductRequest productRequest) {
        ProductResponse response = productService.updateProduct(productId, productRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get Product by SKU", description = "Retrieve a product by its SKU code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/sku/{skuCode}")
    public ResponseEntity<ProductResponse> getProductBySku(@PathVariable("skuCode") String skuCode) {
        ProductResponse productResponse = productService.findBySku(skuCode);
        return ResponseEntity.ok(productResponse);
    }
}