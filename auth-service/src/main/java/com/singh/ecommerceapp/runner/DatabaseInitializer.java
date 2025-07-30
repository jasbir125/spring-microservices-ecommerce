package com.singh.ecommerceapp.runner;
import com.singh.ecommerceapp.security.SecurityConfig;
import com.singh.ecommerceapp.security.oauth2.OAuth2Provider;
import com.singh.ecommerceapp.entity.User;
import com.singh.ecommerceapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userService.getUsers().isEmpty()) {
            return;
        }
        USERS.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
        });
        log.info("Database initialized");
    }

    private static final List<User> USERS = Arrays.asList(
            new User("admin", "admin", "Admin", "admin@abc.com", SecurityConfig.ADMIN, null, OAuth2Provider.LOCAL, "1"),
            new User("user", "user", "User", "user@abc.com", SecurityConfig.USER, null, OAuth2Provider.LOCAL, "2"),
            new User("jasbirsingh", "Password", "User", "jasbirsingh@abc.com", SecurityConfig.ADMIN, null, OAuth2Provider.LOCAL, "3")
    );

}
