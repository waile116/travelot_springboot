package com.web.travelot.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int SALT_LENGTH = 16;

    // generate random salt
    public static String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt); // generate random bytes
        return Base64.getEncoder().encodeToString(salt); // convert random bytes to string
    }

    // encrypt (SHA-256 + salt)
    public static String encrypt(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");    // get 256 bit hash value
            md.update(salt.getBytes());                                         // convert salt to bytes and update it with hash value
            byte[] hashedPassword = md.digest(password.getBytes());             // convert password to bytes and do hash calculation
            return Base64.getEncoder().encodeToString(hashedPassword);          // convert back to string
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password encryption failed", e);
        }
    }

    // verify
    public static boolean verify(String inputPass, String storedPass, String salt) {
        String encryptInput = encrypt(inputPass, salt);
        System.out.println("input pass: " + inputPass);
        System.out.println("encrypted input pass: " + encryptInput);
        System.out.println("stored pass: " + storedPass);
        return encryptInput.equals(storedPass);
    }
}