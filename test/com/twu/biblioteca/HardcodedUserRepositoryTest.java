package com.twu.biblioteca;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class HardcodedUserRepositoryTest {
    private final UserRepository usersRepository = new HardcodedUserRepository();

    @Test
    void successfulLogin() throws InvalidLibraryNumberException, LoginFailedException {
        var ln = new LibraryNumber("123-4567");
        var pw = new PasswordHash("password123");
        usersRepository.login(ln, pw);
    }

    @Test
    void unsuccessfulLogin() throws InvalidLibraryNumberException {
        var ln = new LibraryNumber("123-4567");
        var pw = new PasswordHash("aoeuidhtns");
        assertThrows(LoginFailedException.class, () -> usersRepository.login(ln, pw));
    }
}
