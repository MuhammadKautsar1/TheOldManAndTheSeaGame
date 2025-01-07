package dao;

import model.MarineLife;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;

public class MarineLifeDAO {
    private static final String SELECT_ALL = "SELECT m.id_marinelife, m.name, m.classification, m.points, m.description, m.id_image, i.data " +
                                           "FROM marinelife m LEFT JOIN images i ON m.id_image = i.id_image";

    public static List<MarineLife> getAllMarineLife() {
        List<MarineLife> marineLifeList = new ArrayList<>();
        
        try (Connection con = BaseDAO.getCon();
             PreparedStatement st = con.prepareStatement(SELECT_ALL);
             ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                byte[] imageData = rs.getBytes("data");
                Image image = null;
                if (imageData != null) {
                    image = new Image(new ByteArrayInputStream(imageData));
                }
                
                MarineLife marineLife = new MarineLife(
                    rs.getInt("id_marinelife"),
                    rs.getString("name"),
                    rs.getString("classification"),
                    rs.getInt("points"),
                    rs.getString("description"),
                    rs.getInt("id_image"),
                    null,  // environment will be set later if needed
                    image
                );
                marineLifeList.add(marineLife);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching marine life: " + e.getMessage());
        }
        return marineLifeList;
    }

    public static MarineLife getMarineLifeById(int id) {
        String query = SELECT_ALL + " WHERE m.id_marinelife = ?";
        
        try (Connection con = BaseDAO.getCon();
             PreparedStatement st = con.prepareStatement(query)) {
            
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    byte[] imageData = rs.getBytes("data");
                    Image image = null;
                    if (imageData != null) {
                        image = new Image(new ByteArrayInputStream(imageData));
                    }
                    
                    return new MarineLife(
                        rs.getInt("id_marinelife"),
                        rs.getString("name"),
                        rs.getString("classification"),
                        rs.getInt("points"),
                        rs.getString("description"),
                        rs.getInt("id_image"),
                        null,
                        image
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching marine life by ID: " + e.getMessage());
        }
        return null;
    }
}