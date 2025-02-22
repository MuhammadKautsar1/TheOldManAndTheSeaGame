package controller;

import dao.HistoryDAO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.History;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class HistoryController {
    
    @FXML
    private Button backBtn;
    @FXML
    private TableView<History> historyTable;
    @FXML
    private TableColumn<History, Integer> colIdHistory;
    @FXML
    private TableColumn<History, String> colLevel;
    @FXML
    private TableColumn<History, String> colDate;
    @FXML
    private TableColumn<History, Integer> colScore;
    @FXML
    private TableColumn<History, String> colUserName;
    private ObservableList<History> historyList;
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        // Inisialisasi kolom
        colIdHistory.setCellValueFactory(new PropertyValueFactory<>("idHistory"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("username"));

        // Ambil data dari DAO dan tampilkan di TableView
        historyList = FXCollections.observableArrayList();
        loadHistoryData();
        historyTable.setItems(historyList);
        playBackgroundMusic();
    }

    private void loadHistoryData() {
        // Ambil data dari DAO
        User currentUser = LoginController.user;
        
        if (currentUser != null){
            int userId = currentUser.getUid();
            List<History> histories = HistoryDAO.getHistoryById(userId);
            historyList.addAll(histories);
        }
        
    }
    
    @FXML
    private void HistoryBack(MouseEvent event) {
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
            JOptionPane.showMessageDialog(null, "Failed to load Main Menu. Please check your FXML file!");
        }
    }
    
    private void playBackgroundMusic() {
        // Tentukan lokasi file musik
        String musicFile = "src/main/java/assets/history.mp3";  // Sesuaikan dengan lokasi file musik Anda
        File musicPath = new File(musicFile);

        if (musicPath.exists()) {
            Media media = new Media(musicPath.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Memutar musik secara terus-menerus
            mediaPlayer.play();
        } else {
            JOptionPane.showMessageDialog(null, "Musik tidak ditemukan!");
        }
    }
    
    private void stopBackgroundMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();  // Stop the music
        }
    }
    
}