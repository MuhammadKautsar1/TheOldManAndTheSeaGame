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
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class MediaController implements Initializable{

	@FXML
	private MediaView mediaView;
	
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		file = new File("D:\\(A)GIT\\TheOldManAndTheSeaGame\\tomasgame\\src\\main\\java\\assets\\Vidio.mp4");
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		mediaPlayer.play();
	}

    @FXML
    private void handleLogin(MouseEvent event) {
        try {
            URL url = new File("src/main/java/view/Login.fxml").toURI().toURL(); 
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) mediaView.getScene().getWindow(); 

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat halaman login. Periksa file FXML Anda!");
        }
    }

    @FXML
    private void handleLogin(KeyEvent event) {
        try {
            URL url = new File("src/main/java/view/Login.fxml").toURI().toURL(); 
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) mediaView.getScene().getWindow(); 

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat halaman login. Periksa file FXML Anda!");
        }
    }
}