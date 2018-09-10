package com.twu.biblioteca;

import java.util.List;
import java.util.Scanner;

class BibliotecaApp {
    private String welcomeMsg = "Welcome to Biblioteca!";
    public static final String invalidOptionMsg = "Select a valid option!";
    private static final String checkoutSuccessMsg = "Thank you! Enjoy the book";
    private static final String checkoutFailureMsg = "That book is not available";
    private static final String returnSuccessMsg = "Thank you for returning the book.";
    private static final String returnFailureMsg = "That is not a valid book to return.";

    private BookRepository bookRepo = new HardcodedBookRepository();

    private BibliotecaApp() {
    }

    public BibliotecaApp(String welcomeMsg, BookRepository bookRepo) {
        this.welcomeMsg = welcomeMsg;
        this.bookRepo = bookRepo;
    }

    public static void main(String[] args) {
        var app = new BibliotecaApp();
        app.start();
    }

    private void start() {
        showWelcomeMessage();
        var mainMenu = new Options("Please select an option:");
        mainMenu.addOption("list", "List books");
        mainMenu.addOption("return", "Return book");
        mainMenu.addOption("quit", "Quit");
        while (true) {
            try {
                var option = mainMenu.getChoice();
                switch (option) {
                    case "list":
                        this.listBooks();
                        break;
                    case "return":
                        this.returnBook();
                        break;
                    case "quit":
                        System.out.println("Good-bye!");
                        System.exit(0);
                }
            } catch (InvalidChoiceException e) {
                System.out.println(invalidOptionMsg);
            }
        }
    }

    private void showWelcomeMessage() {
        System.out.println(welcomeMsg);
    }

    private void listBooks() {
        var table = new Table("Title", "Author", "Year Published");
        List<Book> books = bookRepo.listBooks();
        for (Book book : books) {
            table.addRow(book.title, book.author, Integer.toString(book.yearPublished));
        }
        while (true) {
            System.out.println(table);
            var options = new Options("Which book would you like to borrow?");
            for (Book book : books) {
                options.addOption(book.title, book.title);
            }
            options.addOption("back", "Back to main menu");
            try {
                var title = options.getChoice();
                if (title.equals("back")) {
                    return;
                }
                var success = bookRepo.checkoutTitle(title);
                if (success) {
                    System.out.println(checkoutSuccessMsg);
                    return;
                } else {
                    System.out.println(checkoutFailureMsg);
                }
            } catch (InvalidChoiceException e) {
                System.out.println(invalidOptionMsg);
            }
        }
    }

    private void returnBook() {
        System.out.println("What is the title of the book you wish to return?");
        var sc = new Scanner(System.in);
        var title = sc.nextLine();
        if (this.bookRepo.returnTitle(title)) {
            System.out.println(returnSuccessMsg);
        } else {
            System.out.println(returnFailureMsg);
        }
    }
}
