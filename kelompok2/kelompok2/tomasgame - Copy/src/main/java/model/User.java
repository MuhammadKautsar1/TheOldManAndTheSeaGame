package model;

public class User { // Tambahkan modifier public
    private String username;
    private String password;

    // Konstruktor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter untuk username
    public String getUsername() {
        return username;
    }

    // Getter untuk password
    public String getPassword() {
        return password;
    }
}
