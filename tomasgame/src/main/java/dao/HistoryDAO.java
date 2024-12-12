package dao;

import model.History;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {

    /**
     * Retrieves the user's history from the database.
     *
     * @param userId the ID of the user whose history is being fetched
     * @return a list of History objects representing the user's game history
     */
    public List<History> getUserHistory(int userId) {
        List<History> historyList = new ArrayList<>();
        String query = "SELECT idHistory, level, date, score, userId FROM history WHERE userId = ?";

        try (Connection con = BaseDAO.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {

            // Bind the userId parameter to the query
            statement.setInt(1, userId);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Iterate through the result set
            while (resultSet.next()) {
                // Retrieve data from the ResultSet
                int idHistory = resultSet.getInt("idHistory");
                String level = resultSet.getString("level");
                Timestamp date = resultSet.getTimestamp("date");
                int score = resultSet.getInt("score");

                // Fetch additional data for the History object
                String userName = fetchUserNameById(userId);
                List<String> fishObtained = fetchFishObtained(idHistory);
                String timePlayed = formatTimePlayed(date);
                int healthPoints = calculateHealthPoints(score);

                // Create the History object
                History history = new History(idHistory, level, date, score, userId, userName, fishObtained, timePlayed, healthPoints);

                // Add the History object to the list
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyList;
    }

    /**
     * Fetches the user's name by their ID.
     *
     * @param userId the ID of the user
     * @return the user's name
     */
    private String fetchUserNameById(int userId) {
        String query = "SELECT username FROM users WHERE id = ?";
        try (Connection con = BaseDAO.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {

            // Bind the userId parameter
            statement.setInt(1, userId);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Return the username if found
            if (resultSet.next()) {
                return resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Unknown"; // Default if no username is found
    }

    /**
     * Fetches the list of fish obtained during a specific game session.
     *
     * @param historyId the ID of the game session
     * @return a list of fish obtained
     */
    private List<String> fetchFishObtained(int historyId) {
        List<String> fishObtained = new ArrayList<>();
        String query = "SELECT fishName FROM fish_obtained WHERE historyId = ?";
        try (Connection con = BaseDAO.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {

            // Bind the historyId parameter
            statement.setInt(1, historyId);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Add each fish name to the list
            while (resultSet.next()) {
                fishObtained.add(resultSet.getString("fishName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fishObtained;
    }

    /**
     * Formats the time played for display.
     *
     * @param timestamp the timestamp of the game session
     * @return a formatted string representing the time played
     */
    private String formatTimePlayed(Timestamp timestamp) {
        // Dummy implementation for now; replace with real logic
        return "00:30:00"; // Example: 30 minutes
    }

    /**
     * Calculates the player's health points based on their score.
     *
     * @param score the player's score
     * @return the calculated health points
     */
    private int calculateHealthPoints(int score) {
        // Example calculation; modify as needed
        return Math.min(score / 10, 100); // Health is capped at 100
    }
}
