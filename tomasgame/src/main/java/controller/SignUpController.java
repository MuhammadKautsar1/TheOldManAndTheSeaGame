package controller;

import static controller.LoginController.user;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.User;
import dao.UserDAO;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.JOptionPane;

public class SignUpController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button loginButton;
    private Stage primaryStage;
    private MediaPlayer mediaPlayer;
    
    public void initialize() {
        playBackgroundMusic();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void handleSignUpButtonEvent(MouseEvent event) throws IOException {
        stopBackgroundMusic();
        
        // Pindah ke halaman Login.fxml
        try {
            URL url = new File("src/main/java/view/Login.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) signUpButton.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat halaman Login. Periksa file FXML Anda!");
        }
    }

    @FXML
    void handleButtonEvent(ActionEvent event) throws IOException {
        stopBackgroundMusic();
    // Validasi input tidak boleh kosong
    if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Username dan Password tidak boleh kosong!");
        return;
    }
    try {
        // Cek apakah username sudah digunakan
        if (UserDAO.isUsernameTaken(usernameField.getText())) {
            JOptionPane.showMessageDialog(null, "Username sudah terdaftar. Silakan gunakan username lain.");
            return;
        }

        // Buat user baru dan lakukan pendaftaran
        User newUser = new User(usernameField.getText(), passwordField.getText(), "password");
        UserDAO.registerUser(newUser);
        JOptionPane.showMessageDialog(null, "Pendaftaran berhasil! Silakan login.");
        stopBackgroundMusic();

        // Pindah ke halaman Login.fxml
        URL url = new File("src/main/java/view/Login.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal memuat halaman Login. Periksa file FXML Anda!");
        }
    }
    
    private void playBackgroundMusic() {
        // Tentukan lokasi file musik
        String musicFile = "src/main/java/assets/login.mp3";  // Sesuaikan dengan lokasi file musik Anda
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
