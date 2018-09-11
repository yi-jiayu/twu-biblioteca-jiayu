package com.twu.biblioteca;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OptionsTest {
    @Test
    void testValid() throws InvalidChoiceException {
        var is = new ByteArrayInputStream("1\n".getBytes());
        var buf = new ByteArrayOutputStream();
        var os = new PrintStream(buf);

        var options = new Options(new Scanner(is), os, "Which book do you want to borrow?");
        options.addOption("hp1", "Harry Potter and the Philosopher's Stone");
        options.addOption("hp2", "Harry Potter and the Chamber of Secrets");
        var selected = options.getChoice();
        assertEquals("hp1", selected);
        String expected = "Which book do you want to borrow?\n" +
                "(1) Harry Potter and the Philosopher's Stone\n" +
                "(2) Harry Potter and the Chamber of Secrets\n" +
                "> ";
        // replace line endings on windows
        String actual = buf.toString().replace("\r\n", "\n");
        assertEquals(expected, actual);
    }

    @Test
    void testNoSuchOption() {
        var is = new ByteArrayInputStream("3\n".getBytes());
        var buf = new ByteArrayOutputStream();
        var os = new PrintStream(buf);

        var options = new Options(new Scanner(is), os, "Which book do you want to borrow?");
        options.addOption("hp1", "Harry Potter and the Philosopher's Stone");
        options.addOption("hp2", "Harry Potter and the Chamber of Secrets");
        assertThrows(InvalidChoiceException.class, options::getChoice);
    }

    @Test
    void testInvalidInput() {
        var is = new ByteArrayInputStream("hello\n".getBytes());
        var buf = new ByteArrayOutputStream();
        var os = new PrintStream(buf);

        var options = new Options(new Scanner(is), os, "Which book do you want to borrow?");
        options.addOption("hp1", "Harry Potter and the Philosopher's Stone");
        options.addOption("hp2", "Harry Potter and the Chamber of Secrets");
        assertThrows(InvalidChoiceException.class, options::getChoice);
    }
}
