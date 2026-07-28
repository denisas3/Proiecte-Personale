package org.example.lab03.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class EncryptUtils {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static final String SECRET_KEY = "mpp";
    private static final String ALGORITHM = "AES";

    private static void prepareSecretKey(String myKey) {
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error preparing secret key", e);
        }
    }

    public static String encryptPassword(String password) {
        try {
            prepareSecretKey(SECRET_KEY);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(password.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting password", e);
        }
    }

    public static String decryptPassword(String encryptedPassword) {
        try {
            prepareSecretKey(SECRET_KEY);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(
                    cipher.doFinal(Base64.getDecoder().decode(encryptedPassword)),
                    StandardCharsets.UTF_8
            );
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting password", e);
        }
    }
}