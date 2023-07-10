package com.bjit.traineeselectionsystem.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingPassword {

    public static String hashPass(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(pass.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception appropriately
            return null;
        }
    }
}
