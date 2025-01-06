package controller;

import dao.ImageDAO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Diver;
import model.MarineLife;
import model.User;
import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Environment;

public class GameController implements Initializable {

    @FXML
    private AnchorPane gamePane1;
    @FXML
    private ImageView diverSprite;
    @FXML
    private Label timerLabel;
    @FXML
    private Button backButton;
    @FXML
    private ImageView marineLifeSprite;

    private static final double CATCH_DISTANCE = 50; // Jarak maksimum untuk menangkap marine life

    private Diver diver;
    private static final int ANIMATION_DURATION = 130;
    private static final int IDLE_ANIMATION_DURATION = 130;

    private static final double MAX_WIDTH = 1280; // Batas lebar 1280
    private static final double MAX_HEIGHT = 720; // Batas tinggi 720

    private int currentFrame = 0;
    private int currentDirectionY = 0;
    private boolean isMoving = false;

    private Timeline animationTimeline;
    private Timeline idleAnimationTimeline;
    private Timeline countdownTimer;
    private Timeline marineLifeSpawnTimeline;
    private Timeline marineLifeMovementTimeline;

    private int remainingTimeInSeconds = 120;

    private Random random = new Random();

    private ArrayList<MarineLife> marineLifeList = new ArrayList<>(); // Daftar MarineLife

    @Override
public void initialize(URL url, ResourceBundle rb) {
    try {
        initializeDiver();
        setupSprite();
        setupKeyboardControls();
        setupAnimation();
        startTimer();
        startMarineLifeSpawning();
        startMarineLifeMovement();
        animateMarineLife(); // Tambahkan animasi
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to initialize game components!");
    }
}


    private void initializeDiver() throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages();
        if (images != null && !images.isEmpty()) {
            byte[] imageData = images.get(0);
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));
            User user = new User("1", "Player", "password");
            diver = new Diver(user, 100, 100, spriteImage);
        } else {
            throw new IllegalStateException("No diver sprite found in the database.");
        }
    }

    private void setupSprite() {
        if (diver != null) {
            diverSprite.setImage(diver.getSprite().getImage());
            diverSprite.setViewport(new Rectangle2D(0, 0, diver.getFrameWidth(), diver.getFrameHeight()));
        }
    }

    private void setupKeyboardControls() {
        diverSprite.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Scene scene = newScene;
                scene.setOnKeyPressed(this::handleKeyPressed);
                scene.setOnKeyReleased(this::handleKeyReleased);
            }
        });
    }

    private void handleKeyPressed(KeyEvent event) {
        if (diver == null) return;

        switch (event.getCode()) {
            case W -> diver.setUpPressed(true);
            case S -> diver.setDownPressed(true);
            case A -> diver.setLeftPressed(true);
            case D -> diver.setRightPressed(true);
        }
        isMoving = true;
        idleAnimationTimeline.stop();
    }

    private void handleKeyReleased(KeyEvent event) {
        if (diver == null) return;
        switch (event.getCode()) {
            case W -> diver.setUpPressed(false);
            case S -> diver.setDownPressed(false);
            case A -> diver.setLeftPressed(false);
            case D -> diver.setRightPressed(false);
        }
        if (!diver.getUpPressed() && !diver.getDownPressed() && !diver.getLeftPressed() && !diver.getRightPressed()) {
            isMoving = false;
            idleAnimationTimeline.play();
        }
    }

    private void startMarineLifeMovement() {
        marineLifeMovementTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0.1), e -> updateMarineLifeMovement())
        );
        marineLifeMovementTimeline.setCycleCount(Timeline.INDEFINITE);
        marineLifeMovementTimeline.play();
    }

        private void updateMarineLifeMovement() {
        ArrayList<MarineLife> marineLifeToRemove = new ArrayList<>(); // Daftar untuk menyimpan MarineLife yang akan dihapus

        for (MarineLife marineLife : marineLifeList) {
            marineLife.move(); // Pergerakan sesuai logika di kelas MarineLife

            // Update posisi sprite
            marineLife.getSprite().setLayoutX(marineLife.getXPosition());
            marineLife.getSprite().setLayoutY(marineLife.getYPosition());

            // Cek apakah MarineLife keluar dari batas kiri atau kanan
            if (marineLife.getXPosition() < 0 || marineLife.getXPosition() > MAX_WIDTH) {
                marineLifeToRemove.add(marineLife); // Tambahkan ke daftar untuk dihapus
            }
        }

        // Hapus MarineLife yang keluar dari layar
        for (MarineLife marineLife : marineLifeToRemove) {
            gamePane1.getChildren().remove(marineLife.getSprite()); // Hapus dari gamePane
            marineLifeList.remove(marineLife); // Hapus dari daftar
        }
    }


    private void setupAnimation() {
        animationTimeline = new Timeline(
            new KeyFrame(Duration.millis(ANIMATION_DURATION), e -> updateAnimation())
        );
        animationTimeline.setCycleCount(Timeline.INDEFINITE);
        animationTimeline.play();
        idleAnimationTimeline = new Timeline(
        new KeyFrame(Duration.millis(IDLE_ANIMATION_DURATION), e -> updateIdleAnimation())
        );
        idleAnimationTimeline.setCycleCount(Timeline.INDEFINITE);
        idleAnimationTimeline.play();
    }

    private void updateAnimation() {
        if (diver == null || diverSprite.getScene() == null) return;
        if (isMoving) {
            currentFrame = (currentFrame + 1) % diver.getFrameCount();
            if (diver.getRightPressed()) {
                currentDirectionY = diver.getRightY();
            } else if (diver.getLeftPressed()) {
                currentDirectionY = diver.getLeftY();
            } else if (diver.getUpPressed()) {
                currentDirectionY = diver.getUpY();
            } else if (diver.getDownPressed()) {
                currentDirectionY = diver.getDownY();
            }
            diverSprite.setViewport(new Rectangle2D(currentFrame * diver.getFrameWidth(), currentDirectionY, diver.getFrameWidth(), diver.getFrameHeight()));
        }

        diver.updatePosition(MAX_WIDTH, MAX_HEIGHT);
        ensureInBounds();

        diverSprite.setX(diver.getXPosition());
        diverSprite.setY(diver.getYPosition());

        checkCollisionWithMarineLife();
    }

    private void checkCollisionWithMarineLife() {
    ArrayList<MarineLife> caughtMarineLifeList = new ArrayList<>();

    double diverX = diver.getXPosition();
    double diverY = diver.getYPosition();

    for (MarineLife marineLife : marineLifeList) {
        double distance = Math.sqrt(Math.pow(marineLife.getXPosition() - diverX, 2) +
                                    Math.pow(marineLife.getYPosition() - diverY, 2));

        if (distance <= CATCH_DISTANCE) {
            caughtMarineLifeList.add(marineLife);
        }
    }

    // Hapus semua marine life yang tertangkap
    for (MarineLife caught : caughtMarineLifeList) {
        marineLifeList.remove(caught);
        removeCaughtMarineLife(caught);
    }
}


    private void updateIdleAnimation() {
        if (diver == null || isMoving) return;

        currentFrame = (currentFrame + 1) % diver.getFrameCount();
        diverSprite.setViewport(new Rectangle2D(
            currentFrame * diver.getFrameWidth(),
            diver.getUpY(),
            diver.getFrameWidth(),
            diver.getFrameHeight()
        ));
    }

    private void ensureInBounds() {
        if (diver.getXPosition() < 0) {
            diver.setXPosition(0);
        } else if (diver.getXPosition() > MAX_WIDTH - diver.getFrameWidth()) {
            diver.setXPosition(MAX_WIDTH - diver.getFrameWidth());
        }
        if (diver.getYPosition() < 0) {
            diver.setYPosition(0);
        } else if (diver.getYPosition() > MAX_HEIGHT - diver.getFrameHeight()) {
            diver.setYPosition(MAX_HEIGHT - diver.getFrameHeight());
        }
    }

    private void startTimer() {
        countdownTimer = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> updateTimer())
        );
        countdownTimer.setCycleCount(Timeline.INDEFINITE);
        countdownTimer.play();
    }

    private void updateTimer() {
        if (remainingTimeInSeconds > 0) {
            remainingTimeInSeconds--;
            int minutes = remainingTimeInSeconds / 60;
            int seconds = remainingTimeInSeconds % 60;
            timerLabel.setText(String.format("TIMER: %02d:%02d", minutes, seconds));
        } else {
            countdownTimer.stop();
            timerLabel.setText("Time's up!");
        }
    }

    private void startMarineLifeSpawning() {
        marineLifeSpawnTimeline = new Timeline(
            new KeyFrame(Duration.seconds(5), e -> spawnMarineLife())
        );
        marineLifeSpawnTimeline.setCycleCount(Timeline.INDEFINITE);
        marineLifeSpawnTimeline.play();
    }

    private double getRandomSpawnInterval() {
        return 5 + random.nextDouble() * 5; // Random antara 5 hingga 10 detik
    }

    private void updateSpawnInterval() {
        marineLifeSpawnTimeline.stop(); // Hentikan timeline lama
        marineLifeSpawnTimeline.getKeyFrames().clear(); // Hapus semua KeyFrame lama
        marineLifeSpawnTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(getRandomSpawnInterval()), e -> {
            spawnMarineLife();
            updateSpawnInterval();
        }));
        marineLifeSpawnTimeline.play(); // Jalankan timeline baru
    }


        private void spawnMarineLife() {
        try {
            double xPosition = random.nextDouble() * (MAX_WIDTH - 50); // Spawn randomly within the screen width
            double yPosition = random.nextDouble() * (MAX_HEIGHT - 50); // Spawn randomly within the screen height
            MarineLife newMarineLife = createMarineLife(xPosition, yPosition);
            marineLifeList.add(newMarineLife);
            gamePane1.getChildren().add(newMarineLife.getSprite());

            // Memindahkan sprite MarineLife ke belakang elemen lainnya
            newMarineLife.getSprite().toBack();

            newMarineLife.startAnimation();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        private MarineLife createMarineLife(double xPosition, double yPosition) throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages();
        if (images != null && !images.isEmpty()) {
            byte[] imageData = images.get(6);
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));

            // Create environment using gamePane's width and height
            Environment environment = new Environment(0, gamePane1.getWidth(), 0, gamePane1.getHeight());

            // Example speed assignment (random between 1 and 3)

            // Create the MarineLife object using the correct constructor
            MarineLife marineLife = new MarineLife(0, "Marine Life", "Classification", 10, "Description", 10, environment, spriteImage);
            marineLife.initializePositionAndDirection(); // Initialize position and movement direction

            return marineLife;
        } else {
            throw new IllegalStateException("No marine life sprite found in the database.");
        }
    }


        private MarineLife catchMarineLife() {
            MarineLife caughtMarineLife = null;
            double diverX = diver.getXPosition();
            double diverY = diver.getYPosition();

            for (MarineLife marineLife : marineLifeList) {
                double distance = Math.sqrt(Math.pow(marineLife.getXPosition() - diverX, 2) +
                                            Math.pow(marineLife.getYPosition() - diverY, 2));

                if (distance <= CATCH_DISTANCE) {
                    caughtMarineLife = marineLife;
                    break;
                }
            }
            return caughtMarineLife;
        }

        private void removeCaughtMarineLife(MarineLife marineLife) {
            gamePane1.getChildren().remove(marineLife.getSprite());
        }

        private void animateMarineLife() {
        marineLifeMovementTimeline = new Timeline(
            new KeyFrame(Duration.millis(50), e -> updateMarineLifeSprite())
        );
        marineLifeMovementTimeline.setCycleCount(Timeline.INDEFINITE);
        marineLifeMovementTimeline.play();
    }

    private void updateMarineLifeSprite() {
        if (marineLifeSprite != null) {
            double newX = marineLifeSprite.getLayoutX() + (random.nextDouble() - 0.5) * 10; 
            double newY = marineLifeSprite.getLayoutY() + (random.nextDouble() - 0.5) * 10; 

            // Pastikan sprite tetap dalam batas layar
            if (newX < 0) newX = 0;
            if (newX > MAX_WIDTH - marineLifeSprite.getFitWidth()) newX = MAX_WIDTH - marineLifeSprite.getFitWidth();
            if (newY < 0) newY = 0;
            if (newY > MAX_HEIGHT - marineLifeSprite.getFitHeight()) newY = MAX_HEIGHT - marineLifeSprite.getFitHeight();

            marineLifeSprite.setLayoutX(newX);
            marineLifeSprite.setLayoutY(newY);
        }
    }
    
   @FXML
    public void handleBack(MouseEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Keluar");
        confirmationAlert.setHeaderText("Apakah Anda yakin ingin keluar?");
        confirmationAlert.setContentText("Progres tidak akan ke save");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
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
    }
}
