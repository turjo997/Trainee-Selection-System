package com.bjit.traineeselectionsystem.utils;

import java.util.Random;

public class UniqueCode {

    public static String generateUniqueCode() {
        // Define the length of the code
        int codeLength = 8;

        // Define the characters to be used in the code
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Create a StringBuilder to store the generated code
        StringBuilder codeBuilder = new StringBuilder();

        // Generate the code by randomly selecting characters from the defined character set
        Random random = new Random();
        for (int i = 0; i < codeLength; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            codeBuilder.append(randomChar);
        }

        // Return the generated code as a string
        return codeBuilder.toString();
    }
}
