package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HardcodedUserRepository implements UserRepository {
    private final Map<LibraryNumber, User> users = new HashMap<>();

    HardcodedUserRepository() {
        List<User> users = new ArrayList<>(3);
        users.add(new User("123-4567", "password123", "John Doe", "johndoe@example.com", "020-30303030"));
        users.add(new User("987-8654", "Password!", "Jane Doe", "janedoe@example.com", "011-20000198"));
        users.add(new User("555-5555", "1qaz2wsx", "John Smith", "johnsmith@example.com", "033-45229320"));
        for (User user : users) {
            this.users.put(user.getLibraryNumber(), user);
        }
    }

    @Override
    public User login(LibraryNumber ln, PasswordHash pw) throws LoginFailedException {
        var u = users.get(ln);
        if (u == null) {
            throw new LoginFailedException();
        }
        if (u.getPasswordHash().equals(pw)) {
            return u;
        } else {
            throw new LoginFailedException();
        }
    }
}
