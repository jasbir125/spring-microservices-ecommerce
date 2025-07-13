package com.singh.ecommerceapp.product.dataloader;

import com.singh.ecommerceapp.product.model.dto.product.CreateProductRequest;
import com.singh.ecommerceapp.product.repository.ProductRepository;
import com.singh.ecommerceapp.product.service.ProductService;
import com.singh.ecommerceapp.product.util.DataImportUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductDataSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final ProductService productService;

    private final DataImportUtility dataImportUtility;

    private final ProductRepository productRepository;

    public ProductDataSeeder(ProductService productService, DataImportUtility dataImportUtility, ProductRepository productRepository) {
        this.productService = productService;
        this.dataImportUtility = dataImportUtility;
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        long startTime = System.currentTimeMillis();

        try {
            long count = productRepository.count();

            if (count == 0) {
                log.info("🟡 No products found in database. Importing from products.json...");

                dataImportUtility.importEntitiesFromJson(
                        "products.json",
                        CreateProductRequest.class,
                        productService::createProduct
                );

                log.info("✅ Product data import completed successfully.");
            } else {
                log.info("ℹ️ Skipping import. Found {} existing products in the database.", count);
            }

        } catch (Exception e) {
            log.error("❌ Failed to initialize product data during application startup", e);
        }

        long duration = System.currentTimeMillis() - startTime;
        log.info("⏱️ Product data initialization completed in {} ms", duration);
    }
}
