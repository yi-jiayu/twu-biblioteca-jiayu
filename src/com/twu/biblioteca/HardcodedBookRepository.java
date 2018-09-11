package com.twu.biblioteca;

import java.util.*;
import java.util.stream.Collectors;

public class HardcodedBookRepository implements BookRepository {
    private final List<Book> books = new ArrayList<>();
    private final Map<String, BookStatus> statuses = new HashMap<>();

    HardcodedBookRepository() {
        books.add(new Book("Compilers: Principles, Techniques, and Tools", "Alfred V. Aho, Monica S. Lam, Ravi Sethi, and Jeffrey D. Ullman", 1986));
        books.add(new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", 1990));
        books.add(new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides", 1994));
        for (Book book : books) {
            statuses.put(book.getTitle(), new BookStatus(book));
        }
    }

    @Override
    public List<Book> listAllBooks() {
        return Collections.unmodifiableList(books);
    }

    @Override
    public List<Book> listAvailableBooks() {
        return statuses.entrySet().stream()
                .filter(e -> e.getValue().checkedOutBy == null)
                .map(e -> e.getValue().book)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized boolean checkoutTitle(User user, String title) {
        var m = statuses.get(title);
        if (m != null && m.checkedOutBy == null) {
            m.checkedOutBy = user;
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean returnTitle(User user, String title) {
        var m = statuses.get(title);
        if (m != null && m.checkedOutBy.equals(user)) {
            m.checkedOutBy = null;
            return true;
        }
        return false;
    }

    private class BookStatus {
        private final Book book;
        private User checkedOutBy = null;

        private BookStatus(Book book) {
            this.book = book;
        }
    }
}
