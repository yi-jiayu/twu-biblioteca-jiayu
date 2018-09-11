package com.twu.biblioteca;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class HardcodedBookRepositoryTest {
    @Test
    void listBooks() {
        var bookRepo = new HardcodedBookRepository();
        var actual = bookRepo.listBooks();
        var expected = new ArrayList<Book>();
        expected.add(new Book("Compilers: Principles, Techniques, and Tools", "Alfred V. Aho, Monica S. Lam, Ravi Sethi, and Jeffrey D. Ullman", 1986));
        expected.add(new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", 1990));
        expected.add(new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides", 1994));
        assertEquals(expected, actual);
    }

    @Test
    void checkoutTitleSuccess() {
        var bookRepo = new HardcodedBookRepository();
        var success = bookRepo.checkoutTitle("Compilers: Principles, Techniques, and Tools");
        assertTrue(success);
        var actual = bookRepo.listBooks();
        var expected = new ArrayList<Book>();
        expected.add(new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", 1990));
        expected.add(new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides", 1994));
        assertEquals(expected, actual);
    }

    @Test
    void checkoutTitleFailure() {
        var bookRepo = new HardcodedBookRepository();
        var success = bookRepo.checkoutTitle("Principles, Techniques, and Tools");
        assertFalse(success);
        var actual = bookRepo.listBooks();
        var expected = new ArrayList<Book>();
        expected.add(new Book("Compilers: Principles, Techniques, and Tools", "Alfred V. Aho, Monica S. Lam, Ravi Sethi, and Jeffrey D. Ullman", 1986));
        expected.add(new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", 1990));
        expected.add(new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides", 1994));
        assertEquals(expected, actual);
    }

    @Test
    void returnTitleSuccess() {
        var bookRepo = new HardcodedBookRepository();
        bookRepo.onLoan.put("Compilers: Principles, Techniques, and Tools", true);
        bookRepo.returnTitle("Compilers: Principles, Techniques, and Tools");
        var actual = bookRepo.listBooks();
        var expected = new ArrayList<Book>();
        expected.add(new Book("Compilers: Principles, Techniques, and Tools", "Alfred V. Aho, Monica S. Lam, Ravi Sethi, and Jeffrey D. Ullman", 1986));
        expected.add(new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", 1990));
        expected.add(new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides", 1994));
        assertEquals(expected, actual);
    }

    @Test
    void returnTitleFailure() {
        var bookRepo = new HardcodedBookRepository();
        bookRepo.books = bookRepo.books.stream().filter(book -> !book.title.equals("Compilers: Principles, Techniques, and Tools")).collect(Collectors.toList());
        bookRepo.onLoan.put("Compilers: Principles, Techniques, and Tools", true);
        bookRepo.returnTitle("Principles, Techniques, and Tools");
        var actual = bookRepo.listBooks();
        var expected = new ArrayList<Book>();
        expected.add(new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", 1990));
        expected.add(new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides", 1994));
        assertEquals(expected, actual);
    }


}
