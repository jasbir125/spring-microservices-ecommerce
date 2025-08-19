package com.singh.ecommerceapp.controller;

import com.singh.ecommerceapp.aspects.LogExecutionTime;
import com.singh.ecommerceapp.aspects.ValidateCustomHeaders;
import com.singh.ecommerceapp.controller.dto.AuthResponse;
import com.singh.ecommerceapp.controller.dto.LoginRequest;
import com.singh.ecommerceapp.controller.dto.SignUpRequest;
import com.singh.ecommerceapp.security.TokenProvider;
import com.singh.ecommerceapp.exceptions.DuplicatedUserInfoException;
import com.singh.ecommerceapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @LogExecutionTime
    @PostMapping("/authenticate")
    @ValidateCustomHeaders(mandatoryHeaders = {"requestId"})
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("LoginRequest : {}", loginRequest);
        String token = authenticateAndGetToken(loginRequest.username(), loginRequest.password());
        log.info("Login Success - token generated : {}", token);
        return new AuthResponse(token);
    }

    @LogExecutionTime
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.info("SignUpRequest : {}", signUpRequest);
        if (userService.hasUserWithUsername(signUpRequest.username())) {
            throw new DuplicatedUserInfoException(String.format("Username %s already been used", signUpRequest.username()));
        }
        if (userService.hasUserWithEmail(signUpRequest.email())) {
            throw new DuplicatedUserInfoException(String.format("Email %s already been used", signUpRequest.email()));
        }

        userService.saveUser(signUpRequest);

        String token = authenticateAndGetToken(signUpRequest.username(), signUpRequest.password());
        log.info("Signup completed  : {}", signUpRequest);
        return new AuthResponse(token);
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }


}
