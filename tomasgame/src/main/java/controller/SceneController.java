package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneController {

    public static void switchScene(Stage stage, String fxmlFileName) {
        try {
            // Construct the path to the FXML file in the java/view directory
            URL fxmlPath = SceneController.class.getResource("/view/" + fxmlFileName);
            if (fxmlPath == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }

            // Load the FXML file using FXMLLoader
            FXMLLoader loader = new FXMLLoader(fxmlPath);
            Scene scene = new Scene(loader.load(), 1920, 1080);

            // Set the stage properties
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

            // Pass the stage to the appropriate controller
            Object controller = loader.getController();
            if (controller instanceof LoginController) {
                ((LoginController) controller).setPrimaryStage(stage);
            } else if (controller instanceof SignUpController) {
                ((SignUpController) controller).setPrimaryStage(stage);
            } else if (controller instanceof MainMenuController) {
                ((MainMenuController) controller).setPrimaryStage(stage);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print any exceptions for debugging
        }
    }
}
