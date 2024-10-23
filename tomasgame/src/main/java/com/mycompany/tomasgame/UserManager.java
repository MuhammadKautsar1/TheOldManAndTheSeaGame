package com.mycompany.tomasgame;

import java.util.HashMap;

class UserManager {
    private final HashMap<String, User> users = new HashMap<>();

    // Fungsi untuk sign up pengguna baru
    public boolean signUp(String username, String password) {
        if (users.containsKey(username)) {
            return false; // Username sudah ada
        }
        users.put(username, new User(username, password));
        return true;
    }

    // Fungsi untuk sign in pengguna
    public boolean signIn(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            return user.getPassword().equals(password);
        }
        return false;
    }
}