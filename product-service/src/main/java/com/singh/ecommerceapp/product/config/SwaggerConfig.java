package com.singh.ecommerceapp.product.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private Info apiInfo(String version) {
        return new Info()
                .title("Product API")
                .version(version)
                .description("This is a demo Product API documented with Swagger and SpringDoc.");
    }

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI().info(apiInfo("default"));
    }

    @Bean
    public GroupedOpenApi productApiV1() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/api/v1/**")
                .addOpenApiCustomizer(openApi -> openApi.info(apiInfo("v1")))
                .build();
    }

    @Bean
    public GroupedOpenApi productApiV2() {
        return GroupedOpenApi.builder()
                .group("v2")
                .pathsToMatch("/api/v2/**")
                .addOpenApiCustomizer(openApi -> openApi.info(apiInfo("v2")))
                .build();
    }
}