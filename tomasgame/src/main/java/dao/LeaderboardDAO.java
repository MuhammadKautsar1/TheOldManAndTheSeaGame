package dao;

import static dao.BaseDAO.getCon;
import model.Leaderboard;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LeaderboardDAO {

    private static PreparedStatement st;
    private static Connection con;


    public static List<Leaderboard> getLeaderboardByLevel(String level) {
    List<Leaderboard> leaderboardList = new ArrayList<>();
    Set<Integer> uniqueUserIds = new HashSet<>(); // Set untuk memeriksa id_user unik
    String query = """
        SELECT 
            ROW_NUMBER() OVER (ORDER BY score DESC) AS rank,
            user.id_user,
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
        WHERE 
            history.level = ? 
        ORDER BY 
            history.score DESC
    """;

    try (Connection con = getCon();
         PreparedStatement st = con.prepareStatement(query)) {
        
        // Set the level parameter to filter by level
        st.setString(1, level);

        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                int userId = rs.getInt("id_user");
                // Jika ID pengguna belum ada di Set, tambahkan ke leaderboard
                if (!uniqueUserIds.contains(userId)) {
                    uniqueUserIds.add(userId); // Tandai id_user sebagai sudah diproses
                    int rank = rs.getInt("rank");
                    String username = rs.getString("username");
                    String levelResult = rs.getString("level");
                    int score = rs.getInt("score");
                    String date = rs.getString("date");

                    Leaderboard leaderboard = new Leaderboard(rank, username, levelResult, score, date);
                    leaderboardList.add(leaderboard);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return leaderboardList;
    }
}