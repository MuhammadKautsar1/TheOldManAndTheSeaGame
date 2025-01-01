package model;

public class Leaderboard {
    private int rank;
    private String username;
    private String level;
    private int score;
    private String date;

    public Leaderboard(int rank, String username, String level, int score, String date) {
        this.rank = rank;
        this.username = username;
        this.level = level;
        this.score = score;
        this.date = date;
    }

    // Getters and Setters
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
