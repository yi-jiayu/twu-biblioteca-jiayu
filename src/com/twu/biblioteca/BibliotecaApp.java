package com.twu.biblioteca;

import java.util.List;
import java.util.Scanner;

public class BibliotecaApp {
    private String welcomeMsg = "Welcome to Biblioteca!";
    private String checkoutSuccessMsg = "Thank you! Enjoy the book";
    private String checkoutFailureMsg = "That book is not available";
    private String returnSuccessMsg = "Thank you for returning the book.";
    private String returnFailureMsg = "That is not a valid book to return.";

    private BookRepository bookRepo = new HardcodedBookRepository();

    public BibliotecaApp() {
    }

    public BibliotecaApp(String welcomeMsg, BookRepository bookRepo) {
        this.welcomeMsg = welcomeMsg;
        this.bookRepo = bookRepo;
    }

    public static void main(String[] args) {
        var app = new BibliotecaApp();
        app.start();
    }

    public void start() {
        showWelcomeMessage();
        var mainMenu = new Options("Please select an option:");
        mainMenu.setInvalidInputErrorText("Select a valid option!");
        mainMenu.setInvalidChoiceErrorText("Select a valid option!");
        mainMenu.addOption("list", "List books");
        mainMenu.addOption("return", "Return book");
        mainMenu.addOption("quit", "Quit");
        while (true) {
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
