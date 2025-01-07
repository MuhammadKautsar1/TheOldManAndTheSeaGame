package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.MarineLife;
import model.User;

import static dao.BaseDAO.getCon;
import static dao.BaseDAO.closeCon;

public class UserCatchDAO {

    /**
     * Menambahkan tangkapan pengguna ke tabel user_catch.
     * Jika data sudah ada, proses insert akan dilewati.
     */
    public static void addUserCatch(int userId, int marineLifeId) {
        try (Connection con = getCon()) {
            // Cek apakah data sudah ada
            String checkQuery = "SELECT COUNT(*) FROM user_catch WHERE id_user = ? AND id_marinelife = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, marineLifeId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Data already exists. Skipping insert.");
                    return; // Jika data sudah ada, hentikan proses
                }
            }

            // Jika tidak ada, tambahkan data
            String insertQuery = "INSERT INTO user_catch (id_user, id_marinelife) VALUES (?, ?)";
            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, marineLifeId);
                insertStmt.executeUpdate();
                System.out.println("Data inserted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error while adding user catch: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mengambil daftar tangkapan pengguna berdasarkan userId.
     * Mengembalikan daftar objek MarineLife.
     */
    public static List<MarineLife> getUserCatchBook(int userId) {
        List<MarineLife> marineLifeList = new ArrayList<>();
        String query = "SELECT id_marinelife FROM user_catch WHERE id_user = ?";
        try (Connection con = getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MarineLife marineLife = MarineLifeDAO.getMarineLifeById(resultSet.getInt("id_marinelife"));
                if (marineLife != null) {
                    marineLifeList.add(marineLife);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user catch book: " + e.getMessage());
            e.printStackTrace();
        }
        return marineLifeList;
    }

    /**
     * Mengambil daftar ID tangkapan pengguna berdasarkan userId.
     * Mengembalikan Set berisi ID ikan yang sudah ditangkap.
     */
    public static Set<Integer> getUserCatchIds(int userId) {
        Set<Integer> marineLifeIds = new HashSet<>();
        String query = "SELECT id_marinelife FROM user_catch WHERE id_user = ?";
        try (Connection con = getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                marineLifeIds.add(resultSet.getInt("id_marinelife"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user catch IDs: " + e.getMessage());
            e.printStackTrace();
        }
        return marineLifeIds;
    }
}
