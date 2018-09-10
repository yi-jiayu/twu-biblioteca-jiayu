package com.twu.biblioteca;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordHashTest {
    @Test
    public void passwordsMatch() {
        var pw1 = new PasswordHash("password123");
        var pw2 = new PasswordHash("password123");
        assertEquals(pw1, pw2);
    }

    @Test
    public void passwordsDoNotMatch() {
        var pw1 = new PasswordHash("asdfghjkl;");
        var pw2 = new PasswordHash("aoeuidhtns");
        assertNotEquals(pw1, pw2);
    }
}
