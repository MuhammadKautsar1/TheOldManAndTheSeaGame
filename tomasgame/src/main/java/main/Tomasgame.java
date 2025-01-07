package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Tomasgame extends Application {

    @Override  
    public void start(Stage stage) throws IOException {
        try {
            // Load the Login.fxml file from the resources folder (view package)
            // Make sure that Login.fxml is placed under src/main/resources/view/
            URL url = new File("src/main/java/view/Media.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);  // Load FXML from the file URL

            // Create a scene from the FXML file
            Scene scene = new Scene(root, 1280, 720); // Set desired scene size

            // Set the title for the stage and set the scene to it
            stage.setTitle("Tomasgame - Kelompok 2");
            stage.setScene(scene);
            stage.centerOnScreen();  // Center the window on the screen
            stage.show();  // Show the stage
        } catch (Exception e) {
            e.printStackTrace();  // Print any exceptions for debugging
        }
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
        
    }
}
