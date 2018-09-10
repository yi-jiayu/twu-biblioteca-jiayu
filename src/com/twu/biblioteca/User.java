package com.twu.biblioteca;

public class User {
    private LibraryNumber libraryNumber;
    private PasswordHash passwordHash;
    private String name;
    private String email;
    private String phone;

    public User() {
    }

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

    LibraryNumber getLibraryNumber() {
        return libraryNumber;
    }

    PasswordHash getPasswordHash() {
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
