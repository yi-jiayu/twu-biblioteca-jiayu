package com.twu.biblioteca;

import java.util.List;
import java.util.Scanner;

class BibliotecaApp {
    private String welcomeMsg = "Welcome to Biblioteca!";
    private static final String invalidOptionMsg = "Select a valid option!";
    private static final String checkoutSuccessMsg = "Thank you! Enjoy the book";
    private static final String checkoutFailureMsg = "That book is not available";
    private static final String returnSuccessMsg = "Thank you for returning the book.";
    private static final String returnFailureMsg = "That is not a valid book to return.";

    private final BookRepository bookRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    private BibliotecaApp() {
        bookRepository = new HardcodedBookRepository();
        movieRepository = new HardcodedMovieRepository();
        userRepository = new HardcodedUserRepository();
    }

    public BibliotecaApp(String welcomeMsg, BookRepository bookRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.welcomeMsg = welcomeMsg;
        this.bookRepository = bookRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        var app = new BibliotecaApp();
        app.start();
    }

    private void start() {
        showWelcomeMessage();
        var mainMenu = new Options("Please select an option:");
        mainMenu.addOption("listBooks", "List books");
        mainMenu.addOption("returnBook", "Return book");
        mainMenu.addOption("listMovies", "List movies");
        mainMenu.addOption("quit", "Quit");
        while (true) {
            try {
                var option = mainMenu.getChoice();
                switch (option) {
                    case "listBooks":
                        this.listBooks();
                        break;
                    case "returnBook":
                        this.returnBook();
                        break;
                    case "listMovies":
                        this.listMovies();
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

    private void listMovies() {
        var table = new Table("Name", "Year", "Director", "Rating");
        var availableMovies = movieRepository.listAvailableMovies();
        for (Movie movie : availableMovies) {
            table.addRow(movie.getName(), Integer.toString(movie.getYear()), movie.getDirector(), movie.getRating());
        }
        System.out.println(table);
    }

    private void listBooks() {
        var table = new Table("Title", "Author", "Year Published");
        List<Book> books = bookRepository.listAvailableBooks();
        for (Book book : books) {
            table.addRow(book.getTitle(), book.getAuthor(), Integer.toString(book.getYear()));
        }
        System.out.println(table);
        borrowBooks(books);
    }

    private void borrowBooks(List<Book> books) {
        while (true) {
            var options = new Options("Which book would you like to borrow?");
            for (Book book : books) {
                options.addOption(book.getTitle(), book.getTitle());
            }
            options.addOption("back", "Back to main menu");
            try {
                var title = options.getChoice();
                if (title.equals("back")) {
                    return;
                }
                var success = bookRepository.checkoutTitle(new User(), title);
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
        if (this.bookRepository.returnTitle(new User(), title)) {
            System.out.println(returnSuccessMsg);
        } else {
            System.out.println(returnFailureMsg);
        }
    }
}
