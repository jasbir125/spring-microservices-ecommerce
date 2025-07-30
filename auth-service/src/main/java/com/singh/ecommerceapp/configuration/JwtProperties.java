package com.singh.ecommerceapp.configuration;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ToString
@Data
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
    private String secret;
    private Expiration expiration;
    private String type;
    private String issuer;
    private String audience;

    @Data
    public static class Expiration {
        private long minutes;
    }
}