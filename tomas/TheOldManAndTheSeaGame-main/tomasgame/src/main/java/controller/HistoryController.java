package controller;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.History;
import dao.HistoryDAO;

public class HistoryController implements Initializable {

    @FXML
    private HBox hBox; // Bind ke elemen HBox di FXML

    private final HistoryDAO historyDAO = new HistoryDAO(); // Menggunakan DAO untuk mendapatkan data

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int userId = 1; // Ganti dengan ID pengguna yang sesuai

        // Ambil data sejarah pengguna menggunakan HistoryDAO
        List<History> histories = historyDAO.getUserHistory(userId);

        // Iterasi untuk setiap entry dan tampilkan secara dinamis
        for (History history : histories) {
            VBox vbox = createHistoryPane(history);
            hBox.getChildren().add(vbox);
        }
    }

    private VBox createHistoryPane(History history) {
        VBox vbox = new VBox();
        vbox.setPrefWidth(329.0);
        vbox.setPrefHeight(618.0);
        vbox.setStyle("-fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: #f4f4f4;");

        // Level Label
        String level = history.getLevel();
        Label labelLevel = new Label(level.toUpperCase());
        labelLevel.setPrefHeight(168.0);
        labelLevel.setPrefWidth(329.0);
        labelLevel.setStyle("-fx-font-size: 67px; -fx-alignment: center;");

        // Score Pane
        Pane scorePane = new Pane();
        scorePane.setPrefHeight(329.0);
        scorePane.setPrefWidth(329.0);

        int score = history.getScore();
        Label labelScore = new Label(String.valueOf(score));
        labelScore.setLayoutX(191.0);
        labelScore.setLayoutY(245.0);
        labelScore.setPrefHeight(78.0);
        labelScore.setPrefWidth(138.0);
        labelScore.setStyle("-fx-font-size: 37px; -fx-alignment: center;");

        Label labelScoreText = new Label("SCORE:");
        labelScoreText.setLayoutX(155.0);
        labelScoreText.setLayoutY(292.0);
        labelScoreText.setStyle("-fx-font-size: 18px;");

        scorePane.getChildren().addAll(labelScore, labelScoreText);

        // Date Label
        Timestamp date = history.getDate();
        Label labelTime = new Label(date.toString());
        labelTime.setPrefHeight(79.0);
        labelTime.setPrefWidth(329.0);
        labelTime.setStyle("-fx-font-size: 27px; -fx-alignment: center;");

        vbox.getChildren().addAll(labelLevel, scorePane, labelTime);

        return vbox;
    }
}