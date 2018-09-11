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
        var result = repo.checkoutMovie(new User(), "Avatar");
        assertTrue(result);
        var available = repo.listAvailableMovies();
        var expected = repo.listAllMovies().stream()
                .filter(m -> !m.getName().equals("Avatar"))
                .toArray();
        assertThat(available, containsInAnyOrder(expected));
    }

    @Test
    void onlyUserWhoCheckedOutMovieCanReturn() {
        var repo = new HardcodedMovieRepository();
        repo.checkoutMovie(new User(), "Avatar");
        var result = repo.returnMovie(new User(), "Avatar");
        assertFalse(result);
    }

    @Test
    void movieAvailableAgainAfterReturn() {
        var repo = new HardcodedMovieRepository();
        var user = new User();
        repo.checkoutMovie(user, "Avatar");
        var result = repo.returnMovie(user, "Avatar");
        assertTrue(result);
        var all = repo.listAllMovies();
        var available = repo.listAvailableMovies();
        assertThat(available, containsInAnyOrder(all.toArray()));
    }
}
