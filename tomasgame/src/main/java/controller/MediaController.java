package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class MediaController implements Initializable {

    @FXML
    private MediaView mediaView;
    private File videoFile;
    private Media videoMedia;
    private MediaPlayer videoPlayer;
    private File audioFile;
    private Media audioMedia;
    private MediaPlayer audioPlayer;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            // Setup video playback
            videoFile = new File("src/main/java/assets/Vidio.mp4");
            if (!videoFile.exists()) {
                throw new IOException("Video file not found: " + videoFile.getPath());
            }
            videoMedia = new Media(videoFile.toURI().toString());
            videoPlayer = new MediaPlayer(videoMedia);
            mediaView.setMediaPlayer(videoPlayer);

            // Play the video
            videoPlayer.play();

            // Setup audio playback
            audioFile = new File("src/main/java/assets/media.mp3");
            if (!audioFile.exists()) {
                throw new IOException("Audio file not found: " + audioFile.getPath());
            }
            audioMedia = new Media(audioFile.toURI().toString());
            audioPlayer = new MediaPlayer(audioMedia);

            // Play the audio (no loop)
            audioPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error initializing media: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogin(MouseEvent event) {
        navigateToLogin();
    }

    @FXML
    private void handleLogin(KeyEvent event) {
        navigateToLogin();
    }

    private void navigateToLogin() {
        try {
            // Stop both video and audio players before navigating
            if (videoPlayer != null) {
                videoPlayer.stop();
            }
            if (audioPlayer != null) {
                audioPlayer.stop();
            }

            // Navigate to the login page
            URL url = new File("src/main/java/view/Login.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) mediaView.getScene().getWindow();
            
            // Load new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat halaman login. Periksa file FXML Anda!");
        }
    }
}