package com.singh.ecommerceapp.encrypt;

import io.jsonwebtoken.Jwts;
import java.security.PrivateKey;
import java.util.Base64;
import io.jsonwebtoken.Claims;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

public class JwtDecryptor {

    public static void decryptJwt(String jwt) throws Exception {
        PrivateKey privateKey = loadPrivateKey();

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(privateKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Data: " + claims.get("data"));
    }

    private static PrivateKey loadPrivateKey() throws Exception {
        InputStream is = JwtDecryptor.class.getResourceAsStream("/certs/private.key");
        String key = new String(is.readAllBytes())
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }
}