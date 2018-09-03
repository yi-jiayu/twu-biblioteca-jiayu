package com.twu.biblioteca;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class OptionsTest {

    @Test
    public void test() {
        var is = new ByteArrayInputStream("1".getBytes());
        var buf = new ByteArrayOutputStream();
        var os = new PrintStream(buf);

        var options = new Options(is, os, "Which book do you want to borrow?");
        options.addOption("hp1", "Harry Potter and the Philosopher's Stone");
        options.addOption("hp2", "Harry Potter and the Chamber of Secrets");
        var selected = options.getChoice();
        assertEquals("hp1", selected);
    }
}
