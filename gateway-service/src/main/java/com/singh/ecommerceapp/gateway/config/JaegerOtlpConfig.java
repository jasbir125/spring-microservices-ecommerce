package com.singh.ecommerceapp.gateway.config;

import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class JaegerOtlpConfig {

	@Bean
	public WebClient webClient() {
		return WebClient.create();
	}

	OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url) {
		log.debug("Gateway service -  jaeger tracing url : {}",url);
		return OtlpHttpSpanExporter.builder().setEndpoint(url).build();
	}
}
