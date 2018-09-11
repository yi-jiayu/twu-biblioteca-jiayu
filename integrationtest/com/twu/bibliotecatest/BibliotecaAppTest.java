package com.twu.bibliotecatest;

import com.twu.biblioteca.BibliotecaApp;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BibliotecaAppTest {
    @Test
    void testLogin() {
        var input = new String[]{
                "5", // login
                "123-4567", // username
                "password123", //password
                "7", // quit
        };
        var is = new ByteArrayInputStream(String.join("\n", input).getBytes());
        var buf = new ByteArrayOutputStream();
        var os = new PrintStream(buf);

        var app = new BibliotecaApp(is, os);
        app.start();
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
        String actual = buf.toString().replace("\r\n", "\n");
        assertEquals(expected, actual);
    }
}
