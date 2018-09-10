package com.twu.biblioteca;

import java.util.Objects;
import java.util.regex.Pattern;

class LibraryNumber {
    private static final Pattern p = Pattern.compile("\\d{3}-\\d{4}");
    private String value;

    LibraryNumber(String value) throws InvalidLibraryNumberException {
        var m = p.matcher(value);
        if (m.matches()) {
            this.value = value;
        } else {
            throw new InvalidLibraryNumberException();
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryNumber that = (LibraryNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
