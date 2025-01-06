package controller;

/*import dao.ImageDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import dao.UserCatchDAO;
import dao.UserDAO;
import model.MarineLife;

public class BookController implements Initializable {

    @FXML
    private Button ikan1Button;
    @FXML
    private Button ikan2Button;
    @FXML
    private Button backButton;

    @FXML
    private AnchorPane gamePane1;
    @FXML
    private AnchorPane gamePane2;
    @FXML
    private AnchorPane gamePane3;
    @FXML
    private AnchorPane gamePane4;
    @FXML
    private AnchorPane gamePane5;
    @FXML
    private AnchorPane gamePane6;
    @FXML
    private AnchorPane gamePane7;
    @FXML
    private AnchorPane gamePane8;
    @FXML
    private AnchorPane gamePane9;

    private MarineLife fish; // Fish object for animation

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    try {
        initializeFish(gamePane1, 50, 0, 0);   // First fish
        initializeFish(gamePane2, 50, 0, 192); // Second fish
        initializeFish(gamePane3, 50, 0, 384); // Third fish
        initializeFish(gamePane4, 50, 0, 576); // Fourth fish
        initializeFish(gamePane5, 50, 0, 768); // Fifth fish
        initializeFish(gamePane6, 50, 0, 960); // Sixth fish
        initializeFish(gamePane7, 50, 0, 1152); // Seventh fish
        initializeFish(gamePane8, 50, 0, 1344); // Eighth fish
        initializeFish(gamePane9, 50, 0, 1536); // Ninth fish
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to load fish data from the database!");
    }
}

    private void initializeFish(AnchorPane pane, double xPosition, double yPosition, int leftY) throws SQLException {
    ArrayList<byte[]> images = ImageDAO.getImages(); // Fetch the sprite sheet from the database
    if (images != null && !images.isEmpty()) {
        byte[] imageData = images.get(0);
        Image spriteImage = new Image(new ByteArrayInputStream(imageData));

        // Define RIGHT_Y based on LEFT_Y and sprite height
        int rightY = leftY + 96;

        // Initialize the Fish object
        fish = new MarineLife(spriteImage, leftY, rightY);

        // Configure and add the Fish sprite to the scene
        ImageView fishSprite = fish.getSprite();
        fishSprite.setLayoutX(xPosition); // Set the initial X position
        fishSprite.setLayoutY(yPosition); // Set the initial Y position

        // Add the sprite to the specified AnchorPane
        pane.getChildren().add(fishSprite);

        // Start the animation
        fish.startAnimation();
    } else {
        throw new IllegalStateException("No fish sprite found in the database.");
    }
}

    @FXML
    public void handleBack(MouseEvent event) {
        try {
            URL url = new File("src/main/java/view/MainMenu.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) backButton.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load the main menu. Check your FXML file!");
        }
    }
}*/
