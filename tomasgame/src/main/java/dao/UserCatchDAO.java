package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.MarineLife;
import java.util.HashSet; 
import java.util.Set;  

import static dao.BaseDAO.getCon;
import static dao.BaseDAO.closeCon;

public class UserCatchDAO {

    private static PreparedStatement st;
    private static Connection con;

    public static void addUserCatch(int userId, int marineLifeId) {
        try {
            con = getCon();
            String query = "INSERT INTO user_catch (id_user, id_marinelife) VALUES (?, ?)";
            st = con.prepareStatement(query);
            st.setInt(1, userId);
            st.setInt(2, marineLifeId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }

    public static List<MarineLife> getUserCatchBook(int userId) {
        List<MarineLife> marineLifeBook = new ArrayList<>();
        Set<Integer> uniqueMlId = new HashSet<>(); 
        try {
            con = getCon();
            String query = """
                SELECT ml.id_marinelife, ml.name, ml.classification, ml.points, ml.description
                FROM user_catch as uc
                JOIN marinelife as ml ON uc.id_marinelife = ml.id_marinelife
                WHERE uc.id_user = ?
            """;
            st = con.prepareStatement(query);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int marineLifeId = rs.getInt("id_marinelife");

                if (!uniqueMlId.contains(marineLifeId)) {
                    MarineLife marineLife = new MarineLife(
                        marineLifeId,
                        rs.getString("name"),
                        rs.getString("classification"), 
                        rs.getInt("points"),          
                        rs.getString("description")
                    );
                    marineLifeBook.add(marineLife);
                    uniqueMlId.add(marineLifeId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }

        return marineLifeBook;
    }
}
