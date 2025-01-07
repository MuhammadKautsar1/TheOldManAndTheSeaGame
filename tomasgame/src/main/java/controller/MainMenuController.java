package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class MainMenuController {
    
    @FXML
    private Button startGameButton;
    @FXML
    private Button bookButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button leaderboardButton;
    @FXML
    private Button quitButton;
    private MediaPlayer mediaPlayer;
    
    public void initialize() {
        playBackgroundMusic();
    }
   
    @FXML
    private void handleHistory(MouseEvent event) {
        stopBackgroundMusic();
        try {
        URL url = new File("src/main/java/view/History.fxml").toURI().toURL(); 
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) historyButton.getScene().getWindow(); 
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal memuat halaman history. Periksa file FXML Anda!");
        }
    }
    
    @FXML
    void handleLeaderboard(MouseEvent event) throws IOException {
        stopBackgroundMusic();
    try {
        URL url = new File("src/main/java/view/Leaderboard.fxml").toURI().toURL(); 
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) leaderboardButton.getScene().getWindow(); 
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal memuat halaman Login. Periksa file FXML Anda!");
        }
}
    
    @FXML
    void handleQuitButtonEvent(MouseEvent event) throws IOException {
        stopBackgroundMusic();
    // Pindah ke halaman Login.fxml
    try {
        URL url = new File("src/main/java/view/Login.fxml").toURI().toURL(); 
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) quitButton.getScene().getWindow(); 
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal memuat halaman Leaderboard. Periksa file FXML Anda!");
        }
    }
    
    @FXML
    private void handleGame(MouseEvent event) {
        stopBackgroundMusic();
        try {
        URL url = new File("src/main/java/view/Game.fxml").toURI().toURL(); 
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) startGameButton.getScene().getWindow(); 
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal memuat halaman game. Periksa file FXML Anda!");
        }
    }
    
    @FXML
    private void handleBook(MouseEvent event) {
        stopBackgroundMusic();
        try {
        URL url = new File("src/main/java/view/Book.fxml").toURI().toURL(); 
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) bookButton.getScene().getWindow(); 
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal memuat halaman buku. Periksa file FXML Anda!");
        }
    }
    
    private void playBackgroundMusic() {
        // Tentukan lokasi file musik
        String musicFile = "src/main/java/assets/mainmenu.mp3";  // Sesuaikan dengan lokasi file musik Anda
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