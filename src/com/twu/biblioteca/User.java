package com.twu.biblioteca;

public class User {
    private LibraryNumber libraryNumber;
    private final PasswordHash passwordHash;
    private final String name;
    private final String email;
    private final String phone;

    // for creating new users internally without validation
    User(String libraryNumber, String password, String name, String email, String phone) {
        try {
            this.libraryNumber = new LibraryNumber(libraryNumber);
        } catch (InvalidLibraryNumberException ignored) {
        }

        this.passwordHash = new PasswordHash(password);
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public LibraryNumber getLibraryNumber() {
        return libraryNumber;
    }

    public PasswordHash getPasswordHash() {
        return passwordHash;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
