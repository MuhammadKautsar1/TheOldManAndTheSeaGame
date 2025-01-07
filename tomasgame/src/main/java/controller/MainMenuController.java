package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public MainMenuController() {
        // Mulai memutar musik saat controller dibuat
        try {
            String musicFile = "src/main/java/assets/MainMenu.mp3"; // Path ke file musik
            Media sound = new Media(new File(musicFile).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Musik akan looping
            mediaPlayer.play(); // Mulai musik
        } catch (Exception e) {
            System.err.println("Error loading music: " + e.getMessage());
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @FXML
    private void handleHistory(MouseEvent event) {
        stopMusic(); // Hentikan musik sebelum berpindah halaman
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
    void handleLeaderboard(MouseEvent event) {
        stopMusic(); // Hentikan musik sebelum berpindah halaman
        try {
            URL url = new File("src/main/java/view/Leaderboard.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) leaderboardButton.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat halaman leaderboard. Periksa file FXML Anda!");
        }
    }

    @FXML
    void handleQuitButtonEvent(MouseEvent event) {
        stopMusic(); // Hentikan musik sebelum berpindah halaman
        try {
            URL url = new File("src/main/java/view/Login.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) quitButton.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat halaman login. Periksa file FXML Anda!");
        }
    }

    @FXML
    private void handleGame(MouseEvent event) {
        stopMusic(); // Hentikan musik sebelum berpindah halaman
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
        stopMusic(); // Hentikan musik sebelum berpindah halaman
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
}
