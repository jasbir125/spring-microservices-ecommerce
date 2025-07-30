package com.singh.ecommerceapp.service;

import com.singh.ecommerceapp.configuration.JwtProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

public class JwtPropertiesTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(JwtPropertiesTest.Config.class)
            .withPropertyValues(
                    "app.jwt.secret=v9y$B&E)H@MbQeThWmZq4t7w!z%C*F-JaNdRfUjXn2r5u8x/A?D(G+KbPeShVkYp",
                    "app.jwt.expiration.minutes=10",
                    "app.jwt.type=JWT",
                    "app.jwt.issuer=order-api",
                    "app.jwt.audience=order-app"
            );

    @EnableConfigurationProperties(JwtProperties.class)
    static class Config {}

    @Test
    void jwtPropertiesLoadsCorrectly() {
        contextRunner.run(context -> {
            JwtProperties props = context.getBean(JwtProperties.class);
            System.out.println("JWT Secret: " + props.getSecret());
        });
    }
}
