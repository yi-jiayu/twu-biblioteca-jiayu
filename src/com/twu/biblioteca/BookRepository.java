package com.twu.biblioteca;

import java.util.List;

public interface BookRepository {
    List<Book> listBooks();

    boolean checkoutTitle(String title);

    boolean returnTitle(String title);
}
