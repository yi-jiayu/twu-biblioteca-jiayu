package com.twu.biblioteca;

import java.util.Objects;

class Movie {
    private final String name;
    private final int year;
    private final String director;
    private final int rating;

    Movie(String name, int year, String director, int rating) {
        this.name = name;
        this.year = year;
        this.director = director;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public String getRating() {
        if (rating >= 1 && rating <= 10) {
            return Integer.toString(rating);
        } else return "Unrated";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year &&
                rating == movie.rating &&
                Objects.equals(name, movie.name) &&
                Objects.equals(director, movie.director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, director, rating);
    }
}
