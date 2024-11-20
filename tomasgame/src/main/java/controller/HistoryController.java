package controller;

import dao.HistoryDAO;
import model.History;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class HistoryController {

    // Fetch history records for a specific user
    public ObservableList<History> getUserHistory(int userId) {
        List<History> historyList = HistoryDAO.getUserHistory(userId);
        return FXCollections.observableArrayList(historyList); // Convert to ObservableList for JavaFX
    }

    // Add a new history record
    public void addHistory(String level, int score, int userId) {
        History history = new History(0, level, null, score, userId);
        HistoryDAO.addHistory(history); // Call DAO to add history
    }
}
