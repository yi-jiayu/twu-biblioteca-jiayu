package com.twu.biblioteca;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class BibliotecaApp {
    private static final String welcomeMsg = "Welcome to Biblioteca!";
    private static final String invalidOptionMsg = "Select a valid option!";
    private static final String checkoutBookSuccess = "Thank you! Enjoy the book";
    private static final String checkoutBookFailure = "That book is not available";
    private static final String checkoutMovieSuccess = "Thank you! Enjoy the movie";
    private static final String checkoutMovieFailure = "That movie is not available";
    private static final String returnBookSuccess = "Thank you for returning the book.";
    private static final String returnBookFailure = "That is not a valid book to return.";
    private static final String returnMovieSuccess = "Thank you for returning the movie.";
    private static final String returnMovieFailure = "That is not a valid movie to return.";
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
                        this.returnMovie();
                        break;
                    case "login":
                        this.login();
                        break;
                    case "accountInfo":
                        this.accountInfo();
                        break;
                    case "logout":
                        this.logout();
                        break;
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
        var movies = movieRepository.listAvailableMovies();
        for (Movie movie : movies) {
            table.addRow(movie.getName(), Integer.toString(movie.getYear()), movie.getDirector(), movie.getRating());
        }
        this.os.println(table);
        this.borrowMovie(movies);
    }

    private void borrowMovie(List<Movie> movies) {
        if (user == null) {
            this.os.println("You must be logged in to borrow movies.");
            return;
        }

        while (true) {
            var options = new Options(this.sc, this.os, "Which movie would you like to borrow?");
            for (Movie movie : movies) {
                options.addOption(movie.getName(), movie.getName());
            }
            options.addOption("back", "Back to main menu");
            try {
                var name = options.getChoice();
                if (name.equals("back")) {
                    return;
                }
                var success = movieRepository.checkoutMovie(user, name);
                if (success) {
                    this.os.println(checkoutMovieSuccess);
                    return;
                } else {
                    this.os.println(checkoutMovieFailure);
                }
            } catch (InvalidChoiceException e) {
                this.os.println(invalidOptionMsg);
            }
        }
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
        if (user == null) {
            this.os.println("You must be logged in to borrow books.");
            return;
        }

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
                var success = bookRepository.checkoutTitle(user, title);
                if (success) {
                    this.os.println(checkoutBookSuccess);
                    return;
                } else {
                    this.os.println(checkoutBookFailure);
                }
            } catch (InvalidChoiceException e) {
                this.os.println(invalidOptionMsg);
            }
        }
    }

    private void returnBook() {
        if (user == null) {
            this.os.println("You must be logged in to return books.");
            return;
        }
        var borrowed = bookRepository.getBorrowedBooks(user);
        if (borrowed.isEmpty()) {
            this.os.println("You do not have any books to return.");
            return;
        }
        var options = new Options(this.sc, this.os, "Which book would you like to return?");
        for (Book book : borrowed) {
            options.addOption(book.getTitle(), book.getTitle());
        }
        options.addOption("back", "Cancel");
        while (true) {
            try {
                var title = options.getChoice();
                if (title.equals("back")) {
                    return;
                }
                var success = bookRepository.returnTitle(user, title);
                if (success) {
                    this.os.println(returnBookSuccess);
                    return;
                } else {
                    this.os.println(returnBookFailure);
                }
            } catch (InvalidChoiceException e) {
                this.os.println(invalidOptionMsg);
            }
        }
    }

    private void returnMovie() {
        if (user == null) {
            this.os.println("You must be logged in to return movies.");
            return;
        }
        var borrowed = movieRepository.getBorrowedMovies(user);
        if (borrowed.isEmpty()) {
            this.os.println("You do not have any movies to return.");
            return;
        }
        var options = new Options(this.sc, this.os, "Which book would you like to return?");
        for (Movie movie : borrowed) {
            options.addOption(movie.getName(), movie.getName());
        }
        options.addOption("back", "Cancel");
        while (true) {
            try {
                var title = options.getChoice();
                if (title.equals("back")) {
                    return;
                }
                var success = movieRepository.returnMovie(user, title);
                if (success) {
                    this.os.println(returnMovieSuccess);
                    return;
                } else {
                    this.os.println(returnMovieFailure);
                }
            } catch (InvalidChoiceException e) {
                this.os.println(invalidOptionMsg);
            }
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

    private void logout() {
        this.user = null;
        this.os.println("Logged out!");
    }

    private void accountInfo() {
        this.os.printf("Library number : %s\n", user.getLibraryNumber());
        this.os.printf("Name           : %s\n", user.getName());
        this.os.printf("Email          : %s\n", user.getEmail());
        this.os.printf("Phone          : %s\n", user.getPhone());
    }
}
