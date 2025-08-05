package com.singh.ecommerceapp.encrypt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        PrivateKey privateKey = KeyLoader.loadPrivateKey("private.key");
        PublicKey publicKey = KeyLoader.loadPublicKey("public.crt");

        JwtEncryptor jwtEncryptor = new JwtEncryptor(privateKey, publicKey);

        Map<String, Object> claims = Map.of(
                "userId", "abc123",
                "role", "admin"
        );

        String jwt = jwtEncryptor.createEncryptedJwt(claims);
        System.out.println("Generated JWT:\n" + jwt);

        Jws<Claims> parsed = jwtEncryptor.decryptJwt(jwt);
        System.out.println("\nDecoded Claims:");
        parsed.getBody().forEach((k, v) -> System.out.println(k + ": " + v));





    }
}