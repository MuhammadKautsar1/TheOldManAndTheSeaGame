package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MainMenuController {

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleStartGame() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Started");
        alert.setHeaderText(null);
        alert.setContentText("Game is starting!");
        alert.showAndWait();
    }

    @FXML
    private void handleBook() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Book");
        alert.setHeaderText(null);
        alert.setContentText("Book feature is under construction.");
        alert.showAndWait();
    }

    @FXML
    private void handleHistory() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("History");
        alert.setHeaderText(null);
        alert.setContentText("History feature is under construction.");
        alert.showAndWait();
    }

    @FXML
    private void handleLeaderboard() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Leaderboard");
        alert.setHeaderText(null);
        alert.setContentText("Leaderboard feature is under construction.");
        alert.showAndWait();
    }

    @FXML
    private void handleQuit() {
        primaryStage.close();
    }
}
