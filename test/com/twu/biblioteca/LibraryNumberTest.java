package com.twu.biblioteca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryNumberTest {
    @Test
    void validLibraryNumber() throws InvalidLibraryNumberException {
        String raw = "123-4567";
        var ln = new LibraryNumber(raw);
        assertEquals(raw, ln.toString());
    }

    @Test
    void invalidLibraryNumber() {
        assertThrows(InvalidLibraryNumberException.class, () -> new LibraryNumber("1234567"));
    }
}
