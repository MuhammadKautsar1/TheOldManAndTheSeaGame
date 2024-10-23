package com.mycompany.tomasgame;

import model.User;
import java.util.HashMap;

class UserManager {
    private final HashMap<String, User> users = new HashMap<>();

    // Fungsi untuk sign up pengguna baru
    public boolean signUp(String username, String password) {
        if (users.containsKey(username)) {
            return false; // Username sudah ada
        }
        // Buat user baru dan tambahkan ke dalam HashMap
        users.put(username, new User(username, password)); // Menggunakan konstruktor yang benar
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
