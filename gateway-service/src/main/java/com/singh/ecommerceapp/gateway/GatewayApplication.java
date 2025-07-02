package com.singh.ecommerceapp.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
@RestController
public class GatewayApplication {


	public static void main(String[] args) {
		log.info("Initializing gateway-service");
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	@Lazy(false)
	public Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> apis(RouteDefinitionLocator locator,
																  SwaggerUiConfigParameters swaggerUiConfigParameters) {
		Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
		List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
		definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")).forEach(routeDefinition -> {
			String name = routeDefinition.getId().replaceAll("-service", "");
			AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl(name, "/" + name, null);
			urls.add(swaggerUrl);
		});
		swaggerUiConfigParameters.setUrls(urls);
		return urls;
	}
	@GetMapping
	public String geHealthStatus(){
		log.info("Gateway service is up");
		return "Gateways-Service is up.";
	}

}
