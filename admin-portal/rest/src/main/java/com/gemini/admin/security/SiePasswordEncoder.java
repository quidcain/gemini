package com.gemini.admin.security;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/17/18
 * Time: 2:43 PM
 */
public class SiePasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return encrypt(digest(rawPassword));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }

    private String encrypt(byte[] digestedBytes) {
        return new String(Base64.encodeBase64(digestedBytes));
    }

    private byte[] digest(CharSequence password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            return digest.digest(password.toString().getBytes());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}