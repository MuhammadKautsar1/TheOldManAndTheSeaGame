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
}
