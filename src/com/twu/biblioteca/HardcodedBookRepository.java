package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HardcodedBookRepository implements BookRepository {
    List<Book> books = new ArrayList<>();
    Map<String, Boolean> onLoan = new HashMap<>();

    HardcodedBookRepository() {
        books.add(new Book("Compilers: Principles, Techniques, and Tools", "Alfred V. Aho, Monica S. Lam, Ravi Sethi, and Jeffrey D. Ullman", 1986));
        books.add(new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", 1990));
        books.add(new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides", 1994));
    }

    @Override
    public List<Book> listBooks() {
        return books.stream().filter(book -> !onLoan.getOrDefault(book.title, false)).collect(Collectors.toList());
    }

    @Override
    public synchronized boolean checkoutTitle(String title) {
        // check if book exists
        if (books.stream().anyMatch(book -> book.title.equals(title))) {
            // return false if title on loan
            if (onLoan.getOrDefault(title, false)) {
                return false;
            }
            onLoan.put(title, true);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean returnTitle(String title) {
        if (onLoan.getOrDefault(title, false)) {
            onLoan.remove(title);
            return true;
        }
        return false;
    }
}
