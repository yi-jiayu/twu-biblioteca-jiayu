package com.twu.biblioteca;

import org.junit.Test;

public class HardcodedUserRepositoryTest {
    private final UserRepository usersRepository = new HardcodedUserRepository();

    @Test
    public void successfulLogin() throws InvalidLibraryNumberException, LoginFailedException {
        var ln = new LibraryNumber("123-4567");
        var pw = new PasswordHash("password123");
        usersRepository.login(ln, pw);
    }

    @Test(expected = LoginFailedException.class)
    public void unsuccessfulLogin() throws InvalidLibraryNumberException, LoginFailedException {
        var ln = new LibraryNumber("123-4567");
        var pw = new PasswordHash("aoeuidhtns");
        usersRepository.login(ln, pw);
    }
}
