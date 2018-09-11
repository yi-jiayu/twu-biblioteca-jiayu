package com.twu.biblioteca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PasswordHashTest {
    @Test
    void passwordsMatch() {
        var pw1 = new PasswordHash("password123");
        var pw2 = new PasswordHash("password123");
        assertEquals(pw1, pw2);
    }

    @Test
    void passwordsDoNotMatch() {
        var pw1 = new PasswordHash("asdfghjkl;");
        var pw2 = new PasswordHash("aoeuidhtns");
        assertNotEquals(pw1, pw2);
    }
}
