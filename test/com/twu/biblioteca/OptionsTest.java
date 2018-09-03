package com.twu.biblioteca;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class OptionsTest {
    @Test
    public void testValid() {
        var is = new ByteArrayInputStream("1\n".getBytes());
        var buf = new ByteArrayOutputStream();
        var os = new PrintStream(buf);

        var options = new Options(is, os, "Which book do you want to borrow?");
        options.addOption("hp1", "Harry Potter and the Philosopher's Stone");
        options.addOption("hp2", "Harry Potter and the Chamber of Secrets");
        var selected = options.getChoice();
        assertEquals("hp1", selected);
        String expected = "Which book do you want to borrow?\n" +
                "(1) Harry Potter and the Philosopher's Stone\n" +
                "(2) Harry Potter and the Chamber of Secrets\n";
        // replace line endings on windows
        String actual = buf.toString().replace("\r\n", "\n");
        assertEquals(expected, actual);
    }

    @Test
    public void testNoSuchOption() {
        var is = new ByteArrayInputStream("3\n".getBytes());
        var buf = new ByteArrayOutputStream();
        var os = new PrintStream(buf);

        var options = new Options(is, os, "Which book do you want to borrow?");
        options.addOption("hp1", "Harry Potter and the Philosopher's Stone");
        options.addOption("hp2", "Harry Potter and the Chamber of Secrets");
        try {
            options.getChoice();
        } catch (RuntimeException ignored) {
        }
        String expected = "Which book do you want to borrow?\n" +
                "(1) Harry Potter and the Philosopher's Stone\n" +
                "(2) Harry Potter and the Chamber of Secrets\n" +
                "No such option! Please try again:\n";
        // replace line endings on windows
        String actual = buf.toString().replace("\r\n", "\n");
        assertEquals(expected, actual);
    }

    @Test
    public void testInvalidInput() {
        var is = new ByteArrayInputStream("hello\n".getBytes());
        var buf = new ByteArrayOutputStream();
        var os = new PrintStream(buf);

        var options = new Options(is, os, "Which book do you want to borrow?");
        options.addOption("hp1", "Harry Potter and the Philosopher's Stone");
        options.addOption("hp2", "Harry Potter and the Chamber of Secrets");
        try {
            options.getChoice();
        } catch (RuntimeException ignored) {
        }
        String expected = "Which book do you want to borrow?\n" +
                "(1) Harry Potter and the Philosopher's Stone\n" +
                "(2) Harry Potter and the Chamber of Secrets\n" +
                "Enter the number corresponding to your selected option:\n";
        // replace line endings on windows
        String actual = buf.toString().replace("\r\n", "\n");
        assertEquals(expected, actual);
    }
}
