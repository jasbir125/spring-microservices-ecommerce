package com.singh.ecommerceapp.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableMongoAuditing
@EnableDiscoveryClient
@EnableSpringDataWebSupport
public class ProductServiceApplication {
	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(ProductServiceApplication.class, args);
	}

	public static void restart() {
		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(ProductServiceApplication.class);
		});

		thread.setDaemon(false);
		thread.start();
	}

}
