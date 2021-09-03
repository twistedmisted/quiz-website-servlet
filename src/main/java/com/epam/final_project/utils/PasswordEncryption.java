package com.epam.final_project.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncryption {

    private PasswordEncryption() {}

    public static String encrypt(String password) {
        final MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
            final byte[] resultByte = messageDigest.digest();
            return Base64.getEncoder().encodeToString(resultByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
