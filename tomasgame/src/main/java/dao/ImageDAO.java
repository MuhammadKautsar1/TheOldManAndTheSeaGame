/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import static dao.BaseDAO.closeCon;
import static dao.BaseDAO.getCon;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ImageDAO extends BaseDAO{
    private static PreparedStatement st;
    private static Connection con;
    
    
    public static void insertEntry(byte[] imageData, File file) throws SQLException {
        
        try {
            
            con = getCon(); 
            String query = "INSERT INTO images (name, data) VALUES (?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, file.getName()); 
            st.setBytes(2, imageData);
            
           
            int rowsInserted = st.executeUpdate();
            if (rowsInserted > 0) {
                    System.out.println("Image saved successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }
    
    public static ArrayList<byte[]> getImages() {
        ArrayList<byte[]> result = new ArrayList();
        try {
            con = getCon();
            String sql = "SELECT name, data FROM images";
            st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next())
            {
                result.add(rs.getBytes("data"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return result;
    }
    
    public static byte[] getImageById(int imageId) {
    byte[] imageData = null;
    try {
        con = getCon();
        String sql = "SELECT data FROM images WHERE id = ?";
        st = con.prepareStatement(sql);
        st.setInt(1, imageId);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            imageData = rs.getBytes("data");
        }
    } 
    catch (SQLException e) {
        System.err.println("Error fetching image by ID: " + e.getMessage());
    } 
    finally {
        closeCon(con);
    }
    return imageData;
}

}
