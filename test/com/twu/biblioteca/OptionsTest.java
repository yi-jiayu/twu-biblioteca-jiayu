package com.twu.biblioteca;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class OptionsTest {
    @Test
    public void testValid() throws InvalidChoiceException {
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

    @Test(expected = InvalidChoiceException.class)
    public void testNoSuchOption() throws InvalidChoiceException {
        var is = new ByteArrayInputStream("3\n".getBytes());
        var buf = new ByteArrayOutputStream();
        var os = new PrintStream(buf);

        var options = new Options(is, os, "Which book do you want to borrow?");
        options.addOption("hp1", "Harry Potter and the Philosopher's Stone");
        options.addOption("hp2", "Harry Potter and the Chamber of Secrets");
        options.getChoice();
    }

    @Test(expected = InvalidChoiceException.class)
    public void testInvalidInput() throws InvalidChoiceException {
        var is = new ByteArrayInputStream("hello\n".getBytes());
        var buf = new ByteArrayOutputStream();
        var os = new PrintStream(buf);

        var options = new Options(is, os, "Which book do you want to borrow?");
        options.addOption("hp1", "Harry Potter and the Philosopher's Stone");
        options.addOption("hp2", "Harry Potter and the Chamber of Secrets");
        options.getChoice();
    }
}
