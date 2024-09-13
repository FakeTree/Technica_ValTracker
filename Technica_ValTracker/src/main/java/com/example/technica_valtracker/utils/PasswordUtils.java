package com.example.technica_valtracker.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public class PasswordUtils {

    // Method to hash a password using SHA-256
    public static String hashPassword(String password) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Hash the password
            byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert byte array into signum representation (hexadecimal string)
            BigInteger number = new BigInteger(1, hashBytes);
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros for full 32 character hex string
            while (hexString.length() < 64) {
                hexString.insert(0, '0');
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);  // If SHA-256 is not available
        }
    }
}