package com.singh.ecommerceapp.controller;

import com.singh.ecommerceapp.controller.dto.AuthResponse;
import com.singh.ecommerceapp.controller.dto.LoginRequest;
import com.singh.ecommerceapp.controller.dto.SignUpRequest;
import com.singh.ecommerceapp.entity.Role;
import com.singh.ecommerceapp.entity.RoleEnum;
import com.singh.ecommerceapp.security.TokenProvider;
import com.singh.ecommerceapp.security.oauth2.OAuth2Provider;
import com.singh.ecommerceapp.exceptions.DuplicatedUserInfoException;
import com.singh.ecommerceapp.entity.User;
import com.singh.ecommerceapp.service.RoleService;
import com.singh.ecommerceapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private AuthController authController;
    private RoleService roleService;
    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authenticationManager = mock(AuthenticationManager.class);
        tokenProvider = mock(TokenProvider.class);
        roleService = mock(RoleService.class);

        authController = new AuthController(userService, authenticationManager, tokenProvider);
    }

    @Test
    void login_shouldReturnAuthToken() {
        LoginRequest request = new LoginRequest("testuser", "password");

        Authentication authMock = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authMock);
        when(tokenProvider.generate(authMock)).thenReturn("test-jwt-token");

        AuthResponse response = authController.login(request);

        assertEquals("test-jwt-token", response.accessToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void signUp_shouldCreateUserAndReturnToken() {
        Role role = new Role();
        role.setName(RoleEnum.ROLE_USER);
        SignUpRequest request = new SignUpRequest("newuser", "password", "New User", "user@example.com", role);

        when(userService.hasUserWithUsername("newuser")).thenReturn(false);
        when(userService.hasUserWithEmail("user@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");

        Authentication authMock = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authMock);
        when(tokenProvider.generate(authMock)).thenReturn("signup-jwt-token");

        AuthResponse response = authController.signUp(request);

        assertEquals("signup-jwt-token", response.accessToken());

        ArgumentCaptor<SignUpRequest> userCaptor = ArgumentCaptor.forClass(SignUpRequest.class);
        verify(userService).saveUser(userCaptor.capture());

        SignUpRequest savedUser = userCaptor.getValue();
        assertEquals("newuser", savedUser.username());
        //assertEquals("encoded-password", savedUser.getPassword());
        assertEquals("New User", savedUser.name());
        assertEquals("user@example.com", savedUser.email());
        //assertEquals("USER", savedUser.getRole());
    }

    @Test
    void signUp_shouldThrowExceptionWhenUsernameExists() {
        SignUpRequest request = new SignUpRequest("existinguser", "password", "Name", "email@example.com", new Role());

        when(userService.hasUserWithUsername("existinguser")).thenReturn(true);

        DuplicatedUserInfoException exception = assertThrows(
                DuplicatedUserInfoException.class,
                () -> authController.signUp(request)
        );

        assertEquals("Username existinguser already been used", exception.getMessage());
    }

    @Test
    void signUp_shouldThrowExceptionWhenEmailExists() {
        SignUpRequest request = new SignUpRequest("user", "password", "Name", "duplicate@example.com", new Role());

        when(userService.hasUserWithUsername("user")).thenReturn(false);
        when(userService.hasUserWithEmail("duplicate@example.com")).thenReturn(true);

        DuplicatedUserInfoException exception = assertThrows(
                DuplicatedUserInfoException.class,
                () -> authController.signUp(request)
        );

        assertEquals("Email duplicate@example.com already been used", exception.getMessage());
    }
}