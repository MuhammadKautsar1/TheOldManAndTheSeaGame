package controller;

import dao.HistoryDAO;
import dao.MarineLifeDAO;
import dao.UserCatchDAO;
import model.MarineLife;
import model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.HashSet;
import javax.swing.JOptionPane;

public class BookController implements Initializable {
    @FXML
    private Button ikan1Button, ikan2Button, ikan3Button, ikan4Button, 
                   ikan5Button, ikan6Button, ikan7Button, ikan8Button, 
                   ikan9Button, backButton;

    @FXML
    private AnchorPane gamePane1, gamePane2, gamePane3, gamePane4, 
                       gamePane5, gamePane6, gamePane7, gamePane8, gamePane9;

    @FXML
    private ImageView ikan1ImageView, ikan2ImageView, ikan3ImageView, 
                      ikan4ImageView, ikan5ImageView, ikan6ImageView, 
                      ikan7ImageView, ikan8ImageView, ikan9ImageView;

    private List<MarineLife> marineLifeList;
    private Set<Integer> caughtMarineLifeIds;
    private User currentUser;
    private static final String QUESTION_MARK_PATH = "src/main/java/assets/question.png";
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get current user from LoginController
        currentUser = LoginController.user;
        
        if (currentUser == null) {
            showErrorDialog("Please login first!");
            handleBack(null);
            return;
        }

        try {
            marineLifeList = MarineLifeDAO.getAllMarineLife();
            loadUserCatch();
            AnchorPane[] panes = {gamePane1, gamePane2, gamePane3, gamePane4,
                                 gamePane5, gamePane6, gamePane7, gamePane8, gamePane9};
            for (int i = 0; i < panes.length && i < marineLifeList.size(); i++) {
                initializeFishDisplay(panes[i], i);
            }
            playBackgroundMusic();
        } catch (Exception e) {
            showErrorDialog("Failed to load marine life data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadUserCatch() {
        if (currentUser != null) {
            int userId = currentUser.getUid();
            caughtMarineLifeIds = UserCatchDAO.getUserCatchIds(userId);
        } else {
            caughtMarineLifeIds = new HashSet<>();
        }
    }

    private void initializeFishDisplay(AnchorPane pane, int index) {
        if (index >= 0 && index < marineLifeList.size()) {
            MarineLife marineLife = marineLifeList.get(index);
            boolean isCaught = caughtMarineLifeIds.contains(marineLife.getId());
            ImageView targetImageView = (ImageView) pane.getChildren().stream()
                    .filter(node -> node instanceof ImageView)
                    .findFirst()
                    .orElse(null);
            if (targetImageView != null) {
                if (isCaught) {
                    initializeStaticFish(pane, index);
                } else {
                    initializeQuestionMark(targetImageView);
                }
            }
        }
    }

    private void initializeStaticFish(AnchorPane pane, int index) {
        if (index >= 0 && index < marineLifeList.size()) {
            MarineLife marineLife = marineLifeList.get(index);
            ImageView sprite = marineLife.getSprite();
            if (sprite != null) {
                ImageView targetImageView = (ImageView) pane.getChildren().stream()
                        .filter(node -> node instanceof ImageView)
                        .findFirst()
                        .orElse(null);
                if (targetImageView != null) {
                    sprite.setPreserveRatio(true);
                    sprite.setFitWidth(targetImageView.getFitWidth());
                    sprite.setFitHeight(targetImageView.getFitHeight());
                    double offsetX = 40.0; 
                    double offsetY = 10.0; 
                    sprite.setLayoutX(targetImageView.getLayoutX() + offsetX);
                    sprite.setLayoutY(targetImageView.getLayoutY() + offsetY);
                    pane.getChildren().remove(targetImageView);
                    pane.getChildren().add(sprite);
                    marineLife.startAnimation();
                    targetImageView.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
                        sprite.setFitWidth(newBounds.getWidth());
                        sprite.setFitHeight(newBounds.getHeight());
                        sprite.setLayoutX(newBounds.getMinX() + offsetX);
                        sprite.setLayoutY(newBounds.getMinY() + offsetY);
                    });
                }
            }
        }
    }

    private void initializeQuestionMark(ImageView targetImageView) {
        try {
            File questionMarkFile = new File(QUESTION_MARK_PATH);
            if (!questionMarkFile.exists()) {
                System.err.println("Question mark image not found at: " + questionMarkFile.getAbsolutePath());
                return;
            }
            Image questionMarkImage = new Image(questionMarkFile.toURI().toString());
            targetImageView.setImage(questionMarkImage);
            targetImageView.setPreserveRatio(true);
            double offsetX = 40.0;
            double offsetY = 10.0;
            targetImageView.setLayoutX(targetImageView.getLayoutX() + offsetX);
            targetImageView.setLayoutY(targetImageView.getLayoutY() + offsetY);
            targetImageView.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
                targetImageView.setFitWidth(newBounds.getWidth());
                targetImageView.setFitHeight(newBounds.getHeight());
                targetImageView.setLayoutX(newBounds.getMinX() + offsetX);
                targetImageView.setLayoutY(newBounds.getMinY() + offsetY);
            });
        } catch (Exception e) {
            System.err.println("Error loading question mark image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMarineLifeButton(MouseEvent event) {
        if (currentUser == null) {
            showErrorDialog("Please login first!");
            return;
        }

        Button source = (Button) event.getSource();
        int index = Integer.parseInt(source.getId().replaceAll("[^0-9]", "")) - 1;
        if (marineLifeList == null || index < 0 || index >= marineLifeList.size()) {
            showErrorDialog("Invalid fish index.");
            return;
        }

        MarineLife marineLife = marineLifeList.get(index);
        if (!caughtMarineLifeIds.contains(marineLife.getId())) {
            showErrorDialog("You haven't caught this fish yet.");
        } else {
            showMarineLifeInfo(index);
        }
    }

    private void showMarineLifeInfo(int index) {
        if (marineLifeList == null || index < 0 || index >= marineLifeList.size()) {
            showErrorDialog("Marine life data is not available.");
            return;
        }

        MarineLife marineLife = marineLifeList.get(index);
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Fish Information");
        infoAlert.setHeaderText("Details of " + marineLife.getName());

        TextArea textArea = new TextArea(
            "Name: " + marineLife.getName() + "\n\n" +
            "Classification: " + marineLife.getClassification() + "\n\n" +
            "Points: " + marineLife.getPoints() + "\n\n" +
            "Description: " + marineLife.getDescription()
        );
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(10);
        textArea.setPrefColumnCount(40);

        infoAlert.getDialogPane().setContent(textArea);
        infoAlert.showAndWait();
    }

    @FXML
    private void handleBack(MouseEvent event) {
        try {
            stopBackgroundMusic();
            URL url = new File("src/main/java/view/MainMenu.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load Main Menu!");
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void playBackgroundMusic() {
        try {
            String musicPath = "src/main/java/assets/Books.mp3";
            Media media = new Media(new File(musicPath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing background music: " + e.getMessage());
        }
    }

    private void stopBackgroundMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}