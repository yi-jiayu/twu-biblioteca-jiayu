package com.twu.biblioteca;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HardcodedBookRepositoryTest {
    @Test
    void allBooksAvailableBeforeCheckout() {
        var repo = new HardcodedBookRepository();
        var all = repo.listAllBooks();
        var available = repo.listAvailableBooks();
        assertThat(available, containsInAnyOrder(all.toArray()));
    }

    @Test
    void bookNoLongerAvailableAfterCheckout() {
        var repo = new HardcodedBookRepository();
        var book = repo.listAllBooks().stream().findFirst().orElseThrow();
        var result = repo.checkoutTitle(new User(), book.getTitle());
        var available = repo.listAvailableBooks();
        var expected = repo.listAllBooks().stream()
                .filter(m -> !m.getTitle().equals(book.getTitle()))
                .toArray();
        assertTrue(result);
        assertThat(available, containsInAnyOrder(expected));
    }

    @Test
    void onlyUserWhoCheckedOutBookCanReturn() {
        var repo = new HardcodedBookRepository();
        var book = repo.listAllBooks().stream().findFirst().orElseThrow();
        repo.checkoutTitle(new User(), book.getTitle());
        var result = repo.returnTitle(new User(), book.getTitle());
        assertFalse(result);
    }

    @Test
    void bookAvailableAgainAfterReturn() {
        var repo = new HardcodedBookRepository();
        var book = repo.listAllBooks().stream().findFirst().orElseThrow();
        var user = new User();
        repo.checkoutTitle(user, book.getTitle());
        var result = repo.returnTitle(user, book.getTitle());
        var all = repo.listAllBooks();
        var available = repo.listAvailableBooks();
        assertTrue(result);
        assertThat(available, containsInAnyOrder(all.toArray()));
    }

    @Test
    void showBorrowedBooksForUserAfterBorrowing() {
        var repo = new HardcodedBookRepository();
        var book = repo.listAllBooks().stream().findFirst().orElseThrow();
        var user = new User();
        repo.checkoutTitle(user, book.getTitle());
        var expected = new ArrayList<Book>();
        expected.add(book);
        var actual = repo.getBorrowedBooks(user);
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    void showBorrowedBooksForUserAfterReturning() {
        var repo = new HardcodedBookRepository();
        var book = repo.listAllBooks().stream().findFirst().orElseThrow();
        var user = new User();
        repo.checkoutTitle(user, book.getTitle());
        repo.returnTitle(user, book.getTitle());
        var borrowed = repo.getBorrowedBooks(user);
        assertTrue(borrowed.isEmpty());
    }
}
