package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.User;
import dao.UserDAO;

public class SignUpController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleSignUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (UserDAO.isUsernameTaken(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign Up Failed");
            alert.setHeaderText(null);
            alert.setContentText("Username is already taken. Please try another one.");
            alert.showAndWait();
        } else {
            User newUser = new User(username, password);
            UserDAO.registerUser(newUser);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sign Up Successful");
            alert.setHeaderText(null);
            alert.setContentText("You have successfully registered!");
            alert.showAndWait();

            navigateToLogin();
        }
    }

    @FXML
    private void navigateToLogin() {
        // Navigate back to Login screen
        SceneController.switchScene(primaryStage, "Login.fxml");
    }
}
