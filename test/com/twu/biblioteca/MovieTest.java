package com.twu.biblioteca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    @Test
    void getRating() {
        var m = new Movie("Crazy Rich Asians", 2018, "Jon M. Chu", 8);
        assertEquals(m.getRating(), "8");
    }

    @Test
    void getRatingUnrated() {
        var m = new Movie("Spider-Man: Into the Spider-Verse", 2018, "Peter Ramsey, Robert Persichetti Jr., Rodney Rothman", 0);
        assertEquals(m.getRating(), "Unrated");
    }
}
