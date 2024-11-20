package dao;

import static dao.BaseDAO.closeCon;
import static dao.BaseDAO.getCon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.History;
import model.Leaderboard;

public class HistoryDAO {

    private static PreparedStatement st;
    private static Connection con;

    // Add a new history record
    public static void addHistory(History history) {
        try {
            con = getCon();
            String query = "INSERT INTO history (level, score, id_user) VALUES (?, ?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, history.getLevel());
            st.setInt(2, history.getScore());
            st.setInt(3, history.getUserId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }

    // Retrieve all history records for a specific user
    public static List<History> getUserHistory(int userId) {
        List<History> historyList = new ArrayList<>();
        try {
            con = getCon();
            String query = "SELECT * FROM history WHERE id_user = ? ORDER BY date DESC";
            st = con.prepareStatement(query);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                History history = new History(
                    rs.getInt("id_history"),
                    rs.getString("level"),
                    rs.getTimestamp("date"),
                    rs.getInt("score"),
                    rs.getInt("id_user")
                );
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return historyList;
    }

    // Retrieve top scores for the leaderboard
    public static List<Leaderboard> getLeaderboard() {
    List<Leaderboard> leaderboardList = new ArrayList<>();
    String query = """
        SELECT 
            ROW_NUMBER() OVER (ORDER BY score DESC) AS rank,
            user.username, 
            history.level, 
            history.score, 
            history.date 
        FROM 
            history 
        JOIN 
            user 
        ON 
            history.id_user = user.id_user
        ORDER BY 
            history.score DESC
    """;

    try (Connection con = getCon();
         PreparedStatement st = con.prepareStatement(query);
         ResultSet rs = st.executeQuery()) {

        while (rs.next()) {
            int rank = rs.getInt("rank");
            String username = rs.getString("username");
            String level = rs.getString("level");
            int score = rs.getInt("score");
            String date = rs.getString("date");

            Leaderboard leaderboard = new Leaderboard(rank, username, level, score, date);
            leaderboardList.add(leaderboard);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return leaderboardList;
    }
}
