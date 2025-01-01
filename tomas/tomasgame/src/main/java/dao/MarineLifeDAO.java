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

public class MarineLifeDAO {
    
    private static final String SELECT_ALL = 
        "SELECT id_marinelife, name, classification, points, description FROM marinelife";

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
                    rs.getString("description")   
                );
                marineLifeList.add(marineLife);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching marine life: " + e.getMessage());
        }

        return marineLifeList;
    }
}
