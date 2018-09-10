package com.twu.biblioteca;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordHash {
    private byte[] digest;

    PasswordHash(String password) {
        try {
            // intentional usage of insecure md5
            var md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            this.digest = md.digest();
        } catch (NoSuchAlgorithmException ignored) {
            // MD5 is required to be supported by every implementation of the Java platform
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordHash that = (PasswordHash) o;
        return Arrays.equals(digest, that.digest);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(digest);
    }
}
