package com.singh.ecommerceapp.encrypt;

import org.bouncycastle.util.io.pem.PemReader;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import java.util.Base64;

public class CertificateEncryptionUtil {

    public static PublicKey loadPublicKey(String certPath) throws Exception {
        try (InputStream in = CertificateEncryptionUtil.class.getClassLoader().getResourceAsStream(certPath)) {
            if (in == null) {
                throw new FileNotFoundException("Certificate not found at: " + certPath);
            }
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) factory.generateCertificate(in);
            return cert.getPublicKey();
        }
    }

    public static PrivateKey loadPrivateKey(String pemPath) throws Exception {
        InputStream resourceStream = CertificateEncryptionUtil.class.getClassLoader().getResourceAsStream(pemPath);
        if (resourceStream == null) {
            throw new FileNotFoundException("Private key not found at: " + pemPath);
        }

        try (PemReader pemReader = new PemReader(new InputStreamReader(resourceStream, StandardCharsets.UTF_8))) {
            byte[] pemContent = pemReader.readPemObject().getContent();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pemContent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        }
    }

    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        String publicCertPath = "certs/public.crt";
        String privateKeyPath = "certs/private.key";
        String originalMessage = "Hello, this is secret data!";

        PublicKey publicKey = loadPublicKey(publicCertPath);
        PrivateKey privateKey = loadPrivateKey(privateKeyPath);

        // encrypt with public key
        String encrypted = encrypt(originalMessage, publicKey);
        System.out.println("Encrypted: " + encrypted);

        // decrypt with private key
        String decrypted = decrypt(encrypted, privateKey);
        System.out.println("Decrypted: " + decrypted);
    }
}