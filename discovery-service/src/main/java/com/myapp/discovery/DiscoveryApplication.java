package com.myapp.discovery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaServer
@Slf4j
@RestController
public class DiscoveryApplication {

	public static void main(String[] args) {
		log.info("Initializing discovery-service");
		new SpringApplicationBuilder(DiscoveryApplication.class).run(args);
	}
}
