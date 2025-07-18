package com.singh.ecommerceapp.product.service;

import com.singh.ecommerceapp.product.exception.ProductNotFoundException;
import com.singh.ecommerceapp.product.mapper.IProductMapper;
import com.singh.ecommerceapp.product.model.dto.product.UpdateProductRequest;
import com.singh.ecommerceapp.product.model.dto.product.ProductResponse;
import com.singh.ecommerceapp.product.model.dto.product.CreateProductRequest;
import com.singh.ecommerceapp.product.entity.Product;
import com.singh.ecommerceapp.product.repository.ProductRepository;
import com.singh.ecommerceapp.product.util.NullAwareBeanUtils;
import com.singh.ecommerceapp.product.util.SkuGenerator;
import com.singh.ecommerceapp.product.util.SlugGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final SellerService sellerService;
    private final SkuGenerator skuGenerator;
    private final IProductMapper productMapper;

    public ProductService(ProductRepository productRepository, SellerService sellerService, SkuGenerator skuGenerator, IProductMapper productMapper) {
        this.productRepository = productRepository;
        this.sellerService = sellerService;
        this.skuGenerator = skuGenerator;
        this.productMapper = productMapper;
    }

    public void deleteProduct(UUID productId) {
        log.info("ProductService :: deleteProduct :: Attempting to delete product with ID: {}", productId);
        if (!productRepository.existsById(productId)) {
            log.error("ProductService :: deleteProduct :: Product not found: {}", productId);
            throw new ProductNotFoundException("Product not found: " + productId);
        }
        productRepository.deleteById(productId);
        log.info("ProductService :: deleteProduct :: Product deleted successfully, ID: {}", productId);
    }

    public ProductResponse getProductById(UUID productId) {
        log.info("ProductService :: getProductById :: Fetching product with ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("ProductService :: getProductById :: Product not found: {}", productId);
                    return new ProductNotFoundException(productId.toString());
                });
        log.info("ProductService :: getProductById :: Product fetched successfully: {}", product);
        return productMapper.toProductResponse(product);
    }

    public ProductResponse updateProduct(UUID productId, UpdateProductRequest updatedProduct) {
        log.info("ProductService :: updateProduct :: Updating product with ID: {}", productId);
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("ProductService :: updateProduct :: Product not found: {}", productId);
                    return new ProductNotFoundException(productId.toString());
                });

        if (updatedProduct.getSeller() != null) {
            log.debug("ProductService :: updateProduct :: Validating seller existence: {}", updatedProduct.getSeller());
            sellerService.validateSellerExistence(updatedProduct.getSeller());
            existingProduct.setSeller(updatedProduct.getSeller());
        }

        NullAwareBeanUtils.copyNonNullProperties(updatedProduct, existingProduct);
        Product updatedProductEntity = productRepository.save(existingProduct);
        log.info("ProductService :: updateProduct :: Product updated successfully: {}", updatedProductEntity);
        return productMapper.toProductResponse(updatedProductEntity);
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        log.info("ProductService :: createProduct :: Creating new product: {}", createProductRequest);
        Product product = productMapper.toProduct(createProductRequest);
        product.setProductId(UUID.randomUUID());
        product.setSku(skuGenerator.generateSku(
                createProductRequest.getCategory().getFirst(),
                createProductRequest.getBrand(),
                createProductRequest.getColor(),
                createProductRequest.getSize()
        ));
        product.setSlug(SlugGenerator.generateSlug(product));
        Product savedProduct = productRepository.save(product);

        log.info("ProductService :: createProduct :: Product saved: {}", savedProduct);

        return productMapper.toProductResponse(savedProduct);
    }

    public Product validateAndGetProductById(UUID productId) {
        log.debug("ProductService :: validateAndGetProductById :: Validating product existence for ID: {}", productId);
        return productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("ProductService :: validateAndGetProductById :: Product not found: {}", productId);
                    return new ProductNotFoundException(productId.toString());
                });
    }

    public void saveProductReview(Product product) {
        log.info("ProductService :: saveProductReview :: Saving product review for product ID: {}", product.getProductId());
        productRepository.save(product);
        log.info("ProductService :: saveProductReview :: Product review saved successfully for product ID: {}", product.getProductId());
    }

    public ProductResponse findBySku(String sku) {
        log.info("ProductService :: findBySku :: Fetching product by SKU: {}", sku);
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> {
                    log.error("ProductService :: findBySku :: Product not found for SKU: {}", sku);
                    return new ProductNotFoundException(sku);
                });
        log.info("ProductService :: findBySku :: Product fetched successfully for SKU: {}", sku);
        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> getProductsByIds(List<UUID> productIds) {
        log.info("ProductService :: getProductsByIds :: Fetching products by IDs: {}", productIds);
        List<Product> products = productRepository.findByProductIdIn(productIds);
        log.info("ProductService :: getProductsByIds :: Products fetched successfully: {}", products);
        return products.stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
}