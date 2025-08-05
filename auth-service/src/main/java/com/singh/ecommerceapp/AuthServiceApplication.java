package com.singh.ecommerceapp;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.ini4j.Ini;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.singh.ecommerceapp")
public class AuthServiceApplication {
    public static void main(String[] args) throws IOException {
        // reading from ini file
        Ini ini = new Ini(new File("dev.ini"));

        // Read GITHUB section
        String clientId = ini.get("GITHUB", "CLIENT_ID");
        String clientSecret = ini.get("GITHUB", "CLIENT_SECRET");

        // reading from .env file
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
        SpringApplication.run(AuthServiceApplication.class, args);

    }
}