package com.twu.biblioteca;

public class BibliotecaApp {
    private static final String welcomeMsg = "Welcome to Biblioteca! What would you like to do today?";

    public static void main(String[] args) {
        var mainMenu = new Options(welcomeMsg);
        mainMenu.addOption("list", "List books");
        var option = mainMenu.getChoice();
        switch (option) {
            case "list":
                System.out.println("Listing books...");
        }
    }
}
