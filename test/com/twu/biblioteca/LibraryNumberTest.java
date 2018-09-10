package com.twu.biblioteca;

import org.junit.Test;

import static org.junit.Assert.*;

public class LibraryNumberTest {
    @Test
    public void validLibraryNumber() throws InvalidLibraryNumberException {
        String raw = "123-4567";
        var ln = new LibraryNumber(raw);
        assertEquals(raw, ln.toString());
    }

    @Test(expected = InvalidLibraryNumberException.class)
    public void invalidLibraryNumber() throws InvalidLibraryNumberException {
        new LibraryNumber("1234567");
    }
}
