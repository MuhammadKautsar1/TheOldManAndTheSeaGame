package dao;

import model.MarineLife;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.BaseDAO.getCon;
import static dao.BaseDAO.closeCon;
import java.io.ByteArrayInputStream;
import javafx.scene.image.Image;

public class MarineLifeDAO {
    
    private static final String SELECT_ALL = "SELECT id_marinelife, name, classification, points, description, id_image FROM marinelife";

    // Mendapatkan semua data MarineLife dari database
    public static List<MarineLife> getAllMarineLife() {
        List<MarineLife> marineLifeList = new ArrayList<>();
        
        try (Connection con = getCon(); 
             PreparedStatement st = con.prepareStatement(SELECT_ALL);
             ResultSet rs = st.executeQuery()) {
             
            while (rs.next()) {
                // Fetch data dan buat objek MarineLife
                MarineLife marineLife = new MarineLife(
                    rs.getInt("id_marinelife"),  
                    rs.getString("name"),         
                    rs.getString("classification"),
                    rs.getInt("points"),          
                    rs.getString("description"),
                    rs.getInt("id_image")          // Termasuk id_image
                );
                marineLifeList.add(marineLife);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching marine life: " + e.getMessage());
        }

        return marineLifeList;
    }
    
    // Mendapatkan poin dari MarineLife berdasarkan ID
    public static int getMarineLifePoints(int marineLifeId) {
        int points = 0;
        ResultSet rs = null;
        PreparedStatement st = null; // Pastikan PreparedStatement dideklarasikan dengan benar
        Connection con = null; // Pastikan Connection juga diinisialisasi

        try {
            con = getCon();
            String query = "SELECT points FROM marinelife WHERE id_marinelife = ?";
            st = con.prepareStatement(query);
            st.setInt(1, marineLifeId);
            rs = st.executeQuery();

            if (rs.next()) {
                points = rs.getInt("points");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeCon(con);
        }
        return points;
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