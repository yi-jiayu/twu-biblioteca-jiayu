package com.twu.biblioteca;

interface UserRepository {
    User login(LibraryNumber ln, PasswordHash pw) throws LoginFailedException;
}
