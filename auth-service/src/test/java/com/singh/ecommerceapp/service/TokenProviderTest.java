package com.singh.ecommerceapp.service;

import com.singh.ecommerceapp.configuration.JwtProperties;
import com.singh.ecommerceapp.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class TokenProviderTest {

    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private TokenProvider tokenProvider;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Mock JwtProperties behavior
        when(jwtProperties.getSecret()).thenReturn("testSecretKey123456789");
        when(jwtProperties.getIssuer()).thenReturn("test-issuer");
        when(jwtProperties.getAudience()).thenReturn("test-audience");
        //when(jwtProperties.getExpiration().getMinutes()).thenReturn(15L);
        when(jwtProperties.getType()).thenReturn("JWT");
    }

    @Test
    void testGenerateToken() {
        // call your method here and assert
    }
}