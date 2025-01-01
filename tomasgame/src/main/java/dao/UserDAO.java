package dao;

import static dao.BaseDAO.closeCon;
import static dao.BaseDAO.getCon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

public class UserDAO {

    private static PreparedStatement st;
    private static Connection con;

    // Validasi Login
    public static User validate(String name, String password) {
        User u = null;
        try {
            con = getCon();
            String query = "SELECT id_user, username, password FROM user WHERE username = ? AND password = ?";
            st = con.prepareStatement(query);
            st.setString(1, name);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                u = new User(rs.getInt("id_user"), rs.getString("username"), password);
            }
        } catch (SQLException e) {
            System.err.println("Error validating user: " + e.getMessage());
        } finally {
            closeCon(con);
        }
        return u;
    }

    // Registrasi User Baru
    public static void registerUser(User u) {
        try {
            con = getCon();
            String query = "INSERT INTO user (username, password) VALUES (?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, u.getUname());
            st.setString(2, u.getPass());
            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        } finally {
            closeCon(con);
        }
    }

    // Cek Apakah Username Sudah Digunakan
    public static boolean isUsernameTaken(String username) {
        boolean isTaken = false;
        try {
            con = getCon();
            String query = "SELECT COUNT(*) FROM user WHERE username = ?";
            st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                isTaken = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
        } finally {
            closeCon(con);
        }
        return isTaken;
    }

    // Ambil Data User Berdasarkan Username
    public static User getUsername(String username) {
        User u = null;
        try {
            con = getCon();
            String query = "SELECT id_user, username, password FROM user WHERE username = ?";
            st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                u = new User(rs.getInt("id_user"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
        } finally {
            closeCon(con);
        }
        return u;
    }
}
