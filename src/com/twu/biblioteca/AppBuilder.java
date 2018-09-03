package com.twu.biblioteca;

class AppBuilder {
    private String welcomeMsg;
    private BookRepository books;

    AppBuilder welcomeMsg(String welcomeMsg) {
        this.welcomeMsg = welcomeMsg;
        return this;
    }

    AppBuilder bookRepository(BookRepository books) {
        this.books = books;
        return this;
    }

    BibliotecaApp build() {
        return new BibliotecaApp(welcomeMsg, books);
    }
}
