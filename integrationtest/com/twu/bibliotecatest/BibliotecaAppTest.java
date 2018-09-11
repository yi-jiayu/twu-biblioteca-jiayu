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
    void quit() {
        var input = new String[]{
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
                "> Good-bye!\n";
        test(input, expected);
    }

    @Test
    void successfulLogin() {
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
    void unsuccessfulLogin() {
        var input = new String[]{
                "5", // login
                "123-4567", // username
                "badpassword", // bad password
        };
        String expected = "Welcome to Biblioteca!\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log in\n" +
                "(6) Quit\n" +
                "> Library number: Password: Login failed! Check your library number and password again.\n" +
                "Library number: ";
        test(input, expected);
    }

    @Test
    void invalidLibraryNumber() {
        var input = new String[]{
                "5", // login
                "1234567", // invalid library number
        };
        String expected = "Welcome to Biblioteca!\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log in\n" +
                "(6) Quit\n" +
                "> Library number: Invalid library number! Library numbers should be in the format \"xxx-xxxx\"\n" +
                "Library number: ";
        test(input, expected);
    }

    @Test
    void successfulBorrowBook() {
        var input = new String[]{
                "5", // login
                "123-4567", // username
                "password123", // password
                "1", // list books
                "1", // borrow first book
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
                "> | Title                                    | Author                   | Year Published |\n" +
                "| ---------------------------------------- | ------------------------ | -------------- |\n" +
                "| The Lord of the Rings                    | J. R. R. Tolkien         | 1954           |\n" +
                "| The Little Prince                        | Antoine de Saint-Exupéry | 1943           |\n" +
                "| Harry Potter and the Philosopher's Stone | J. K. Rowling            | 1997           |\n" +
                "Which book would you like to borrow?\n" +
                "(1) The Lord of the Rings\n" +
                "(2) The Little Prince\n" +
                "(3) Harry Potter and the Philosopher's Stone\n" +
                "(4) Back to main menu\n" +
                "> Thank you! Enjoy the book\n" +
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
    void mustBeLoggedInToBorrowBook() {
        var input = new String[]{
                "1", // list books,
        };
        String expected = "Welcome to Biblioteca!\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log in\n" +
                "(6) Quit\n" +
                "> | Title                                    | Author                   | Year Published |\n" +
                "| ---------------------------------------- | ------------------------ | -------------- |\n" +
                "| The Lord of the Rings                    | J. R. R. Tolkien         | 1954           |\n" +
                "| The Little Prince                        | Antoine de Saint-Exupéry | 1943           |\n" +
                "| Harry Potter and the Philosopher's Stone | J. K. Rowling            | 1997           |\n" +
                "You must be logged in to borrow books.\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log in\n" +
                "(6) Quit\n" +
                "> ";
        test(input, expected);
    }

    @Test
    void successfulReturnBook() {
        var input = new String[]{
                "5", // login
                "123-4567", // username
                "password123", // password
                "1", // list books
                "1", // borrow first book
                "3", // return book
                "The Lord of the Rings", // book title
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
                "> | Title                                    | Author                   | Year Published |\n" +
                "| ---------------------------------------- | ------------------------ | -------------- |\n" +
                "| The Lord of the Rings                    | J. R. R. Tolkien         | 1954           |\n" +
                "| The Little Prince                        | Antoine de Saint-Exupéry | 1943           |\n" +
                "| Harry Potter and the Philosopher's Stone | J. K. Rowling            | 1997           |\n" +
                "Which book would you like to borrow?\n" +
                "(1) The Lord of the Rings\n" +
                "(2) The Little Prince\n" +
                "(3) Harry Potter and the Philosopher's Stone\n" +
                "(4) Back to main menu\n" +
                "> Thank you! Enjoy the book\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log out\n" +
                "(6) Account information\n" +
                "(7) Quit\n" +
                "> What is the title of the book you wish to return?\n" +
                "Thank you for returning the book.\n" +
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
    void mustBeLoggedInToReturnBook() {
        var input = new String[]{
                "3", // return book,
        };
        String expected = "Welcome to Biblioteca!\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log in\n" +
                "(6) Quit\n" +
                "> You must be logged in to return books.\n" +
                "Please select an option:\n" +
                "(1) List books\n" +
                "(2) List movies\n" +
                "(3) Return book\n" +
                "(4) Return movie\n" +
                "(5) Log in\n" +
                "(6) Quit\n" +
                "> ";
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
