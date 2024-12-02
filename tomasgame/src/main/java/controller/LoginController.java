package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.User;
import dao.UserDAO;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = UserDAO.validate(username, password);

        if (user != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText(null);
            alert.setContentText("Welcome, " + username + "!");
            alert.showAndWait();
            navigateToMainMenu();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSignUpRedirect() {
        // Navigate to Sign Up screen
        SceneController.switchScene(primaryStage, "SignUp.fxml");
    }

    private void navigateToMainMenu() {
        // Navigate to Main Menu screen
        SceneController.switchScene(primaryStage, "MainMenu.fxml");
    }
}
