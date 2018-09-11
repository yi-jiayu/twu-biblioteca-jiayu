package com.twu.biblioteca;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(result);
        var available = repo.listAvailableMovies();
        var expected = repo.listAllMovies().stream()
                .filter(m -> !m.getName().equals(movie.getName()))
                .toArray();
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
        assertTrue(result);
        var all = repo.listAllMovies();
        var available = repo.listAvailableMovies();
        assertThat(available, containsInAnyOrder(all.toArray()));
    }
}
