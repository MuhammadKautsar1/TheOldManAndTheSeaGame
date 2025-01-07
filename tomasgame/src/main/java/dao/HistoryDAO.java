package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.History;

public class HistoryDAO {
    // Koneksi ke database
    private static final String URL = "jdbc:mysql://localhost:3306/tomas";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Metode untuk mengambil riwayat berdasarkan ID user
    public static List<History> getHistoryById(int userId) {
        List<History> historyList = new ArrayList<>();
        String query = "SELECT h.id_history, h.level, h.date, h.score, u.id_user, u.username " +
                       "FROM history h " +
                       "INNER JOIN user u ON h.id_user = u.id_user " +
                       "WHERE u.id_user = ? " +
                       "ORDER BY h.date DESC";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement st = con.prepareStatement(query)) {

            // Set parameter ID user
            st.setInt(1, userId);

            // Eksekusi query dan proses hasilnya
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int idHistory = rs.getInt("id_history");
                    String level = rs.getString("level");
                    Timestamp date = rs.getTimestamp("date");
                    int score = rs.getInt("score");
                    String username = rs.getString("username");

                    // Buat objek History dan tambahkan ke list
                    History history = new History(idHistory, level, date, score, username);
                    historyList.add(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyList;
    }

    // Metode untuk menghitung total skor berdasarkan ID user
    public static int getTotalScore(int userId) {
        int totalScore = 0;
        String query = """
            SELECT SUM(m.points) AS totalScore
            FROM user_catch uc
            JOIN marinelife m ON uc.id_marinelife = m.id_marinelife
            WHERE uc.id_user = ?
        """;

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement st = con.prepareStatement(query)) {

            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    totalScore = rs.getInt("totalScore");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total score by user ID: " + e.getMessage());
            e.printStackTrace();
        }

        return totalScore;
    }
    
    public static boolean addHistory(String level, Timestamp date, int score, int userId) {
    boolean isAdded = false;
    String query = """
        INSERT INTO history (level, date, score, id_user)
        VALUES (?, ?, ?, ?)
    """;

    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement st = con.prepareStatement(query)) {

        st.setString(1, level);
        st.setTimestamp(2, date);
        st.setInt(3, score);
        st.setInt(4, userId);

        int rowsAffected = st.executeUpdate();
        isAdded = rowsAffected > 0;

    } catch (SQLException e) {
        System.err.println("Error adding history: " + e.getMessage());
        e.printStackTrace();
    }

    return isAdded;
}

}