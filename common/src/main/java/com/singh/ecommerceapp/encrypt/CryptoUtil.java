package com.singh.ecommerceapp.encrypt;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class CryptoUtil {

    public static String encryptWithPublicKey(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    public static String decryptWithPrivateKey(String encrypted, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decoded = Base64.getDecoder().decode(encrypted);
        return new String(cipher.doFinal(decoded));
    }

    public static void main(String[] args) throws Exception {


        PublicKey publicKey = KeyLoader.loadPublicKey("certs/public.crt");
        PrivateKey privateKey = KeyLoader.loadPrivateKey("certs/private.key");

        String encrypted = CryptoUtil.encryptWithPublicKey("my-secret-data", publicKey);
        String decrypted = CryptoUtil.decryptWithPrivateKey(encrypted, privateKey);

        System.out.println("Encrypted JWT Payload: " + encrypted);
        System.out.println("Decrypted Payload: " + decrypted);
    }
}