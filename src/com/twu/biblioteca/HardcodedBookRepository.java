package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.List;

public class HardcodedBookRepository implements BookRepository {
    private List<Book> books = new ArrayList<>();

    public HardcodedBookRepository() {
        books.add(new Book("Compilers: Principles, Techniques, and Tools", "Alfred V. Aho, Monica S. Lam, Ravi Sethi, and Jeffrey D. Ullman", 1986));
        books.add(new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", 1990));
        books.add(new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides", 1994));
    }

    @Override
    public List<Book> listBooks() {
        return books;
    }
}
