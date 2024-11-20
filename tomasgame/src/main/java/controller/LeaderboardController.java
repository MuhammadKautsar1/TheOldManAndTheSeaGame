package controller;

import dao.HistoryDAO;
import model.Leaderboard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class LeaderboardController {

    // Fetch leaderboard data
    public ObservableList<Leaderboard> getLeaderboard() {
        // Use the correct type (Leaderboard)
        List<Leaderboard> leaderboardList = HistoryDAO.getLeaderboard();
        return FXCollections.observableArrayList(leaderboardList); // Convert to ObservableList for JavaFX
    }
}
