package com.twu.biblioteca;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class BibliotecaApp {
    private static final String welcomeMsg = "Welcome to Biblioteca!";
    private static final String invalidOptionMsg = "Select a valid option!";
    private static final String checkoutSuccessMsg = "Thank you! Enjoy the book";
    private static final String checkoutFailureMsg = "That book is not available";
    private static final String returnSuccessMsg = "Thank you for returning the book.";
    private static final String returnFailureMsg = "That is not a valid book to return.";
    private final BookRepository bookRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private InputStream is = System.in;
    private PrintStream os = System.out;
    private Scanner sc;
    private User user = null;

    private BibliotecaApp() {
        bookRepository = new HardcodedBookRepository();
        movieRepository = new HardcodedMovieRepository();
        userRepository = new HardcodedUserRepository();
    }

    public BibliotecaApp(InputStream is, PrintStream os) {
        this();
        this.is = is;
        this.os = os;
        this.sc = new Scanner(is);
    }

    public static void main(String[] args) {
        var app = new BibliotecaApp(System.in, System.out);
        app.start();
    }

    public void start() {
        showWelcomeMessage();
        showMainMenu();
    }

    private void showMainMenu() {
        while (true) {
            var mainMenu = new Options(this.sc, this.os, "Please select an option:");
            mainMenu.addOption("listBooks", "List books");
            mainMenu.addOption("listMovies", "List movies");
            mainMenu.addOption("returnBook", "Return book");
            mainMenu.addOption("returnMovie", "Return movie");
            if (this.user == null) {
                mainMenu.addOption("login", "Log in");
            } else {
                mainMenu.addOption("logout", "Log out");
                mainMenu.addOption("accountInfo", "Account information");
            }
            mainMenu.addOption("quit", "Quit");

            try {
                var option = mainMenu.getChoice();
                switch (option) {
                    case "listBooks":
                        this.listBooks();
                        break;
                    case "listMovies":
                        this.listMovies();
                        break;
                    case "returnBook":
                        this.returnBook();
                        break;
                    case "returnMovie":
                        // TODO: 2018-09-11 implement return movie
                        throw new RuntimeException("not implemented!");
                    case "login":
                        this.login();
                        break;
                    case "accountInfo":
                        this.accountInfo();
                        break;
                    case "logout":
                        // TODO: 2018-09-11 implement logout
                        throw new RuntimeException("not implemented!");
                    case "quit":
                        this.os.println("Good-bye!");
                        return;
                }
            } catch (InvalidChoiceException e) {
                this.os.println(invalidOptionMsg);
            }
        }
    }

    private void showWelcomeMessage() {
        this.os.println(welcomeMsg);
    }

    private void listMovies() {
        var table = new Table("Name", "Year", "Director", "Rating");
        var availableMovies = movieRepository.listAvailableMovies();
        for (Movie movie : availableMovies) {
            table.addRow(movie.getName(), Integer.toString(movie.getYear()), movie.getDirector(), movie.getRating());
        }
        this.os.println(table);
    }

    private void listBooks() {
        var table = new Table("Title", "Author", "Year Published");
        List<Book> books = bookRepository.listAvailableBooks();
        for (Book book : books) {
            table.addRow(book.getTitle(), book.getAuthor(), Integer.toString(book.getYear()));
        }
        this.os.println(table);
        borrowBooks(books);
    }

    private void borrowBooks(List<Book> books) {
        while (true) {
            var options = new Options(this.sc, this.os, "Which book would you like to borrow?");
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
                    this.os.println(checkoutSuccessMsg);
                    return;
                } else {
                    this.os.println(checkoutFailureMsg);
                }
            } catch (InvalidChoiceException e) {
                this.os.println(invalidOptionMsg);
            }
        }
    }

    private void returnBook() {
        this.os.println("What is the title of the book you wish to return?");
        var sc = new Scanner(this.is);
        var title = sc.nextLine();
        if (this.bookRepository.returnTitle(new User(), title)) {
            this.os.println(returnSuccessMsg);
        } else {
            this.os.println(returnFailureMsg);
        }
    }

    private void login() {
        while (true) {
            this.os.print("Library number: ");
            LibraryNumber ln;
            try {
                ln = new LibraryNumber(this.sc.nextLine());
            } catch (InvalidLibraryNumberException e) {
                this.os.println("Invalid library number! Library numbers should be in the format \"xxx-xxxx\"");
                continue;
            }

            String password;
            var console = System.console();
            if (console == null) {
                this.os.print("Password: ");
                password = this.sc.nextLine();
            } else {
                var pw = console.readPassword("Password: ");
                password = new String(pw);
            }

            try {
                this.user = userRepository.login(ln, new PasswordHash(password));
                break;
            } catch (LoginFailedException e) {
                this.os.println("Login failed! Check your library number and password again.");
            }
        }
        this.os.printf("Welcome, %s!\n", this.user.getName());
    }

    private void accountInfo() {
        // TODO: 2018-09-11 implement account info
        throw new RuntimeException("not implemented!");
    }
}
