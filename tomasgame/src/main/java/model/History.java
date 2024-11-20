package model;

import java.sql.Timestamp;

public class History {
    private int idHistory;      // Primary key
    private String level;       // Enum: EASY, MEDIUM, HARD
    private Timestamp date;     // Date of the record
    private int score;          // Player's score
    private int userId;         // Foreign key referencing the user table

    // Constructor
    public History(int idHistory, String level, Timestamp date, int score, int userId) {
        this.idHistory = idHistory;
        this.level = level;
        this.date = date;
        this.score = score;
        this.userId = userId;
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
}
