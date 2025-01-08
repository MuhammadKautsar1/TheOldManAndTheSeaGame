package controller;

import dao.LeaderboardDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Leaderboard;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LeaderboardController implements Initializable {

    @FXML
    private TableView<Leaderboard> leaderboardTable;

    @FXML
    private Button backBtn;
    @FXML
    private Button mediumBtn;
    @FXML
    private Button easyBtn;
    @FXML
    private Button hardBtn;

    @FXML
    private TableColumn<Leaderboard, Integer> rankColumn;
    @FXML
    private TableColumn<Leaderboard, String> playersColumn;
    @FXML
    private TableColumn<Leaderboard, Integer> pointsColumn;

    private final String HIGHLIGHT = "-fx-background-color: #0078D7; -fx-text-fill: white;";
    private final String DEFAULT = "-fx-background-color: transparent; -fx-text-fill: black;";
    private MediaPlayer mediaPlayer;

    private LeaderboardDAO leaderboardDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the DAO
        leaderboardDAO = new LeaderboardDAO();

        // Set up the columns for the table
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        playersColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        // Load leaderboard data for the default "EASY" level
        loadLeaderboardData("EASY");
        highlightButton(mediumBtn);
        playBackgroundMusic();
    }

    @FXML
    private void easyClick(MouseEvent event) {
        loadLeaderboardData("EASY");
        highlightButton(easyBtn);
    }

    @FXML
    private void mediumClick(MouseEvent event) {
        loadLeaderboardData("MEDIUM");
        highlightButton(mediumBtn);
    }

    @FXML
    private void hardClick(MouseEvent event) {
        loadLeaderboardData("HARD");
        highlightButton(hardBtn);
    }

    @FXML
    private void LeaderboardBack(MouseEvent event) {
        try {
            // Load MainMenu.fxml and show it in the current stage
            stopBackgroundMusic();
            URL url = new File("src/main/java/view/MainMenu.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) backBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLeaderboardData(String level) {
        // Fetch the leaderboard data for the selected level using LeaderboardDAO
        List<Leaderboard> leaderboardData = leaderboardDAO.getLeaderboardByLevel(level);
        ObservableList<Leaderboard> top10 = FXCollections.observableArrayList();

        // Add the top 10 leaderboard entries
        for (int i = 0; i < Math.min(10, leaderboardData.size()); i++) {
            Leaderboard next = leaderboardData.get(i);
            next.setRank(i + 1); // Set rank based on the order of the list
            top10.add(next);
        }
        // Set the items for the leaderboard table
        leaderboardTable.setItems(top10);
    }

    private void highlightButton(Button activeButton) {
        // Reset the styles for all buttons and highlight the active one
        easyBtn.setStyle(DEFAULT);
        mediumBtn.setStyle(DEFAULT);
        hardBtn.setStyle(DEFAULT);
        activeButton.setStyle(HIGHLIGHT);
    }
    
    private void playBackgroundMusic() {
        // Tentukan lokasi file musik
        String musicFile = "src/main/java/assets/leaderboard.mp3";  // Sesuaikan dengan lokasi file musik Anda
        File musicPath = new File(musicFile);

            Media media = new Media(musicPath.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Memutar musik secara terus-menerus
            mediaPlayer.play();
        
    }
    
    private void stopBackgroundMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();  //musik berhenti
        }
    }
}
