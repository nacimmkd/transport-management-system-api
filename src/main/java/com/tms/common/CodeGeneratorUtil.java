package com.tms.common;

import java.security.SecureRandom;


public class CodeGeneratorUtil {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();


    // ex: DEL-A87B
    public static String generateDeliveryCode() {
        return "DEL-" + generateRandomString(4);
    }

    // ex: AbO93CkZ
    public static String generatePassword() {
        return generateRandomString(8);
    }


    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
