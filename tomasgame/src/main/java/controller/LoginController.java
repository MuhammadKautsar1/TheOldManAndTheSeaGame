package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import dao.UserDAO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.JOptionPane;

public class LoginController implements Initializable {

    public static User user;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;

    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = null;
        loginButton.setText("LOGIN");

        // Mulai musik saat halaman Login.fxml dimuat
        try {
            String musicFile = "src/main/java/assets/login.mp3"; // Path ke file musik
            Media sound = new Media(new File(musicFile).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Musik akan looping
            mediaPlayer.play(); // Mulai musik
        } catch (Exception e) {
            System.err.println("Error loading music: " + e.getMessage());
        }
    }

    @FXML
    private void handleButtonEvent(ActionEvent event) throws IOException {
        // Validasi input tidak boleh kosong
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username dan Password tidak boleh kosong!");
            return;
        }

        if (loginButton.getText().equals("LOGIN")) {
            try {
                user = UserDAO.validate(usernameField.getText(), passwordField.getText());
                if (user != null) {
                    // Hentikan musik sebelum berpindah ke halaman MainMenu.fxml
                    stopMusic();

                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    URL url = new File("src/main/java/view/MainMenu.fxml").toURI().toURL();
                    Parent root = FXMLLoader.load(url);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } else {
                    JOptionPane.showMessageDialog(null, "INVALID USERNAME/PASSWORD!!!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            User u = new User(usernameField.getText(), passwordField.getText(), "password");
            UserDAO.registerUser(u);
            JOptionPane.showMessageDialog(null, "Registered " + usernameField.getText() + " Successfully. Please Login!");

            // Hentikan musik sebelum berpindah ke halaman MainMenu.fxml
            stopMusic();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            URL url = new File("src/main/java/view/MainMenu.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }

    @FXML
    void handleSignUpButtonEvent(MouseEvent event) throws IOException {
        // Hentikan musik sebelum berpindah ke halaman Signup.fxml
        stopMusic();

        try {
            URL url = new File("src/main/java/view/Signup.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat halaman Signup. Periksa file FXML Anda!");
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void showLoginView() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
