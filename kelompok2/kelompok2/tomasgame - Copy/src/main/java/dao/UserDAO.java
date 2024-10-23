package dao;


import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/tomas"; 
    private static final String JDBC_USER = "root"; 
    private static final String JDBC_PASS = " "; 

    // Method untuk mendapatkan koneksi ke database
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    // Method untuk menambahkan user baru ke dalam tabel users
    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
        }
    }

    // Method untuk mengambil semua user dari tabel users
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new User(rs.getString("username"), rs.getString("password")));
            }
        }
        return users;
    }

    // Method untuk mendapatkan user berdasarkan username
    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString("username"), rs.getString("password"));
                }
            }
        }
        return null; // Jika user tidak ditemukan
    }

    // Method untuk menghapus user berdasarkan username
    public boolean deleteUserByUsername(String username) throws SQLException {
        String query = "DELETE FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Jika user berhasil dihapus
        }
    }

    // Method untuk mengupdate password user berdasarkan username
    public boolean updateUserPassword(String username, String newPassword) throws SQLException {
        String query = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Jika update berhasil
        }
    }
}
