package model;

import java.sql.Timestamp;
import java.util.List;

public class History {
    private int idHistory;      // Primary key
    private String level;       // Enum: EASY, MEDIUM, HARD
    private Timestamp date;     // Date of the record
    private int score;          // Player's score
    private int userId;         // Foreign key referencing the user table
    private String userName;    // Added field for username
    private List<String> fishObtained; // Added field for fish obtained
    private String timePlayed; // Added field for formatted time played
    private int healthPoints;  // Added field for health points

    // Full Constructor with all required fields for UI
    public History(int idHistory, String level, Timestamp date, int score, int userId, 
                   String userName, List<String> fishObtained, String timePlayed, int healthPoints) {
        this.idHistory = idHistory;
        this.level = level;
        this.date = date;
        this.score = score;
        this.userId = userId;
        this.userName = userName;
        this.fishObtained = fishObtained;
        this.timePlayed = timePlayed;
        this.healthPoints = healthPoints;
    }

    // Constructor without idHistory (for insert operations)
    public History(String level, Timestamp date, int score, int userId) {
        this.level = level;
        this.date = date;
        this.score = score;
        this.userId = userId;
    }

    // Default Constructor
    public History() {
    }

    // Getters and Setters
    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // New methods for userName, fishObtained, timePlayed, healthPoints
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getFishObtained() {
        return fishObtained;
    }

    public void setFishObtained(List<String> fishObtained) {
        this.fishObtained = fishObtained;
    }

    public String getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(String timePlayed) {
        this.timePlayed = timePlayed;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    // Method to convert database model to UI model
    public static History fromDatabaseModel(History dbHistory, String userName, 
                                             List<String> fishObtained, String timePlayed, 
                                             int healthPoints) {
        return new History(dbHistory.getIdHistory(), dbHistory.getLevel(), dbHistory.getDate(),
                           dbHistory.getScore(), dbHistory.getUserId(), userName, fishObtained, timePlayed, healthPoints);
    }

    // Methods to retrieve difficulty and historyId (for UI)
    public String getDifficulty() {
        return this.level;  // Assuming level is equivalent to difficulty
    }

    public int getHistoryId() {
        return this.idHistory; // Return the history ID
    }
}
