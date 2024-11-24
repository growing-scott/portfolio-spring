package com.portfolio.scott.infrastructure.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtil {

    public static SecretKeySpec deriveKey(char[] password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password, salt, 65536, 256); // 65536 iterations and 256-bit key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] key = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(key, "AES");
    }

    public static String encrypt(String plaintext, char[] password, String salt) throws Exception {
        // Derive the key using PBKDF2
        SecretKeySpec key = deriveKey(password, salt.getBytes(StandardCharsets.UTF_8));

        // Initialize AES cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        // Encrypt the data
        byte[] encryptedData = cipher.doFinal(plaintext.getBytes());

        // Encode the result and return it along with the salt for later decryption
        String encryptedText = Base64.getEncoder().encodeToString(encryptedData);
        return encryptedText;
    }

    public static String decrypt(String encryptedText, char[] password, String salt) throws Exception {
        // Split the salt and encrypted data
        byte[] encryptedData = Base64.getDecoder().decode(encryptedText);

        // Derive the key using the same password and salt
        SecretKeySpec key = deriveKey(password, salt.getBytes(StandardCharsets.UTF_8));

        // Initialize AES cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        // Decrypt the data
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    public static String hash(String plaintext) throws NoSuchAlgorithmException {
        // Get the MessageDigest instance for SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Hash the password (convert to bytes, then digest)
        byte[] hashBytes = digest.digest(plaintext.getBytes(StandardCharsets.UTF_8));

        // Convert the hashed bytes into a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString(); // Return the hash as a hex string
    }
}
