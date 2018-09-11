package com.twu.biblioteca;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HardcodedMovieRepositoryTest {
    @Test
    void allMoviesAvailableBeforeCheckout() {
        var repo = new HardcodedMovieRepository();
        var all = repo.listAllMovies();
        var available = repo.listAvailableMovies();
        assertThat(available, containsInAnyOrder(all.toArray()));
    }

    @Test
    void movieNoLongerAvailableAfterCheckout() {
        var repo = new HardcodedMovieRepository();
        var movie = repo.listAllMovies().stream().findFirst().orElseThrow();
        var result = repo.checkoutMovie(new User(), movie.getName());
        var available = repo.listAvailableMovies();
        var expected = repo.listAllMovies().stream()
                .filter(m -> !m.getName().equals(movie.getName()))
                .toArray();
        assertTrue(result);
        assertThat(available, containsInAnyOrder(expected));
    }

    @Test
    void onlyUserWhoCheckedOutMovieCanReturn() {
        var repo = new HardcodedMovieRepository();
        var movie = repo.listAllMovies().stream().findFirst().orElseThrow();
        repo.checkoutMovie(new User(), movie.getName());
        var result = repo.returnMovie(new User(), movie.getName());
        assertFalse(result);
    }

    @Test
    void movieAvailableAgainAfterReturn() {
        var repo = new HardcodedMovieRepository();
        var movie = repo.listAllMovies().stream().findFirst().orElseThrow();
        var user = new User();
        repo.checkoutMovie(user, movie.getName());
        var result = repo.returnMovie(user, movie.getName());
        var all = repo.listAllMovies();
        var available = repo.listAvailableMovies();
        assertTrue(result);
        assertThat(available, containsInAnyOrder(all.toArray()));
    }


    @Test
    void showBorrowedMoviesForUserAfterBorrowing() {
        var repo = new HardcodedMovieRepository();
        var movie = repo.listAllMovies().stream().findFirst().orElseThrow();
        var user = new User();
        repo.checkoutMovie(user, movie.getName());
        var expected = new ArrayList<Movie>();
        expected.add(movie);
        var actual = repo.getBorrowedMovies(user);
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    void showBorrowedMoviesForUserAfterReturning() {
        var repo = new HardcodedMovieRepository();
        var movie = repo.listAllMovies().stream().findFirst().orElseThrow();
        var user = new User();
        repo.checkoutMovie(user, movie.getName());
        repo.returnMovie(user, movie.getName());
        var borrowed = repo.getBorrowedMovies(user);
        assertTrue(borrowed.isEmpty());
    }
}
