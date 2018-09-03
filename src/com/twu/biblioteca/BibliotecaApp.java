package com.twu.biblioteca;

public class BibliotecaApp {
    private String welcomeMsg = "Welcome to Biblioteca!";
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
        mainMenu.addOption("quit", "Quit");
        while (true) {
            var option = mainMenu.getChoice();
            switch (option) {
                case "list":
                    this.listBooks();
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
        for (Book book : bookRepo.listBooks()) {
            table.addRow(book.title, book.author, Integer.toString(book.yearPublished));
        }
        System.out.println(table);
    }
}
