package com.twu.biblioteca;

import org.junit.Test;

import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class HardcodedMovieRepositoryTest {
    @Test
    public void allMoviesAvailableBeforeCheckout() {
        var repo = new HardcodedMovieRepository();
        var all = repo.listAllMovies();
        var available = repo.listAvailableMovies();
        assertEquals(all, available);
    }

    @Test
    public void movieNoLongerAvailableAfterCheckout() {
        var repo = new HardcodedMovieRepository();
        var result = repo.checkoutMovie(new User(), "Avatar");
        assertTrue(result);
        var available = repo.listAvailableMovies();
        var expected = repo.listAllMovies().stream()
                .filter(m -> !m.getName().equals("Avatar"))
                .collect(Collectors.toList());
        assertEquals(expected, available);
    }

    @Test
    public void onlyUserWhoCheckedOutMovieCanReturn() {
        var repo = new HardcodedMovieRepository();
        repo.checkoutMovie(new User(), "Avatar");
        var result = repo.returnMovie(new User(), "Avatar");
        assertFalse(result);
    }

    @Test
    public void movieAvailableAgainAfterReturn() {
        var repo = new HardcodedMovieRepository();
        var user = new User();
        repo.checkoutMovie(user, "Avatar");
        var result = repo.returnMovie(user, "Avatar");
        assertTrue(result);
        var all = repo.listAllMovies();
        var available = repo.listAvailableMovies();
        assertEquals(all, available);
    }
}
