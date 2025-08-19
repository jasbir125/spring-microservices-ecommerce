package com.singh.ecommerceapp.processor;

import com.singh.ecommerceapp.controller.dto.SignUpRequest;
import com.singh.ecommerceapp.entity.User;
import com.singh.ecommerceapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserEntityProcessor implements EntityProcessor<SignUpRequest> {

    private final UserService userService;

    @Override
    public void process(SignUpRequest signUpRequest) {
        if (!userService.hasUserWithEmail(signUpRequest.email())) {
            User savedUser = userService.saveUser(signUpRequest);
            log.info("User Created   : {} ", savedUser.getUsername());
        }
    }
}
