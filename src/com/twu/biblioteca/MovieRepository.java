package com.twu.biblioteca;

import java.util.List;

interface MovieRepository {
    List<Movie> listAllMovies();

    List<Movie> listAvailableMovies();

    boolean checkoutMovie(User user, String name);

    boolean returnMovie(User user, String name);

    List<Movie> getBorrowedMovies(User user);
}
