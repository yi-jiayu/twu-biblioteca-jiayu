package com.twu.biblioteca;

import java.util.*;
import java.util.stream.Collectors;

public class HardcodedMovieRepository implements MovieRepository {
    private final List<Movie> movies = new ArrayList<>();
    private final Map<String, MovieStatus> statuses = new HashMap<>();

    HardcodedMovieRepository() {
        movies.add(new Movie("Avatar", 2008, "James Cameron", 8));
        movies.add(new Movie("Titanic", 1997, "James Cameron", 8));
        movies.add(new Movie("Star Wars: The Force Awakens", 2015, "J. J. Abrams", 8));
        movies.add(new Movie("Avengers: Infinity War", 2018, "Russo brothers", 9));
        for (Movie movie : movies) {
            statuses.put(movie.getName(), new MovieStatus(movie));
        }
    }

    @Override
    public List<Movie> listAllMovies() {
        return Collections.unmodifiableList(movies);
    }

    @Override
    public List<Movie> listAvailableMovies() {
        return movies.stream()
                .filter(m -> statuses.get(m.getName()).checkedOutBy == null)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized boolean checkoutMovie(User user, String name) {
        var m = statuses.get(name);
        if (m != null && m.checkedOutBy == null) {
            m.checkedOutBy = user;
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean returnMovie(User user, String name) {
        var m = statuses.get(name);
        if (m != null && m.checkedOutBy != null && m.checkedOutBy.equals(user)) {
            m.checkedOutBy = null;
            return true;
        }
        return false;
    }

    private class MovieStatus {
        private final Movie movie;
        private User checkedOutBy = null;

        private MovieStatus(Movie movie) {
            this.movie = movie;
        }
    }
}
