package com.singh.ecommerceapp.encrypt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PublicKey;
import java.util.Date;

import io.jsonwebtoken.*;

import java.security.PrivateKey;
import java.util.Map;

public class JwtEncryptor {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public JwtEncryptor(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String createEncryptedJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hour
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public Jws<Claims> decryptJwt(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(jwt);
    }
}