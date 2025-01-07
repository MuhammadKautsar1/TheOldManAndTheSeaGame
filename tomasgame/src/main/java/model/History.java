package model;

import java.sql.Timestamp;

public class History {
    private int idHistory;
    private String level;
    private Timestamp date;
    private int score;
    private String username;

    // Konstruktor
    public History(int idHistory, String level, Timestamp date, int score, String username) {
        this.idHistory = idHistory;
        this.level = level;
        this.date = date;
        this.score = score;
        this.username = username;
    }

    // Getter dan Setter
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

