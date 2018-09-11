package com.twu.biblioteca;

import java.util.List;

interface BookRepository {
    List<Book> listAllBooks();

    List<Book> listAvailableBooks();

    boolean checkoutTitle(User user, String title);

    boolean returnTitle(User user, String title);
}
