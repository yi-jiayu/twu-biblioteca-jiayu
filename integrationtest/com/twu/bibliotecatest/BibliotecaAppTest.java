package com.twu.bibliotecatest;

import com.twu.biblioteca.BibliotecaApp;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BibliotecaAppTest {
    private static void test(String[] input, String expected) {
        var is = new ByteArrayInputStream(String.join("\n", input).getBytes());
        var buf = new ByteArrayOutputStream();
        var os = new PrintStream(buf);
        var app = new BibliotecaApp(is, os);
        try {
            app.start();
        } catch (NoSuchElementException ignored) {
        } finally {
            String actual = buf.toString().replace("\r\n", "\n");
            assertEquals(expected, actual);
        }
    }

    @Test
    void login() {
        var input = new String[]{
                "5", // login
                "123-4567", // username
                "password123", // password
                "7", // quit
        };
        String expected = "Welcome to Biblioteca!\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log in\n" +
                "(6) Quit\n" +
                "> " +
                "Library number: " +
                "Password: " +
                "Welcome, John Doe!\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log out\n" +
                "(6) Account information\n" +
                "(7) Quit\n" +
                "> Good-bye!\n";
        test(input, expected);
    }

    @Test
    void logout() {
        var input = new String[]{
                "5", // login
                "123-4567", // username
                "password123", // password
                "5", // logout
                "6", // quit
        };
        String expected = "Welcome to Biblioteca!\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log in\n" +
                "(6) Quit\n" +
                "> Library number: Password: Welcome, John Doe!\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log out\n" +
                "(6) Account information\n" +
                "(7) Quit\n" +
                "> Logged out!\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log in\n" +
                "(6) Quit\n" +
                "> Good-bye!\n";
        test(input, expected);
    }

    @Test
    void accountInfo() {
        var input = new String[]{
                "5", // login
                "123-4567", // username
                "password123", // password
                "6", // account info
                "7", // quit
        };
        String expected = "Welcome to Biblioteca!\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log in\n" +
                "(6) Quit\n" +
                "> Library number: Password: Welcome, John Doe!\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log out\n" +
                "(6) Account information\n" +
                "(7) Quit\n" +
                "> Library number: 123-4567\n" +
                "Name: John Doe\n" +
                "Email: johndoe@example.com\n" +
                "Phone:  020-30303030\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log out\n" +
                "(6) Account information\n" +
                "(7) Quit\n" +
                "> Good-bye!\n";
        test(input, expected);
    }
}
