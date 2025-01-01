package controller;

import dao.ImageDAO;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Diver;
import model.User;

public class GameController implements Initializable {

    @FXML
    private AnchorPane gamePane;

    @FXML
    private ImageView spriteImageView;

    @FXML
    private Label timerLabel; 

    private Diver diver;
    private static final int ANIMATION_DURATION = 130;
    private static final int IDLE_ANIMATION_DURATION = 130;

    private int currentFrame = 0;
    private int currentDirectionY = 0;
    private boolean isMoving = false;

    private Timeline animationTimeline;
    private Timeline idleAnimationTimeline;
    private Timeline countdownTimer; 

    private int remainingTimeInSeconds = 120;  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gamePane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            updateAnimation();
        });

        gamePane.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            updateAnimation();
        });

        try {
            this.initializeDiver();
            this.setupSprite();
            this.setupKeyboardControls();
            this.setupAnimation();
            this.startTimer();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeDiver() throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages(); 
        if (images != null && !images.isEmpty()) {
            byte[] imageData = images.get(0); 
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));
            User user = new User("1", "Player", "password"); 
            diver = new Diver(user, 100, 100, 32, spriteImage);
        } else {
            throw new IllegalStateException("Tidak ada gambar yang ditemukan di database.");
        }
    }

    private void setupSprite() {
        spriteImageView.setImage(diver.getSprite().getImage());
        spriteImageView.setViewport(new Rectangle2D(0, 0, diver.getFrameWidth(), diver.getFrameHeight()));
    }

    private void setupKeyboardControls() {
        spriteImageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Scene scene = newScene;
                scene.setOnKeyPressed(this::handleKeyPressed);
                scene.setOnKeyReleased(this::handleKeyReleased);
            }
        });
    }

    private void handleKeyPressed(KeyEvent event) {
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

            spriteImageView.setViewport(new Rectangle2D(currentFrame * diver.getFrameWidth(), currentDirectionY, diver.getFrameWidth(), diver.getFrameHeight()));
        }

        double paneWidth = spriteImageView.getScene().getWidth();
        double paneHeight = spriteImageView.getScene().getHeight();

        diver.updatePosition(paneWidth, paneHeight);

        if (diver.getXPosition() < 0) {
            diver.setXPosition(0);
        } else if (diver.getXPosition() > paneWidth - diver.getFrameWidth()) {
            diver.setXPosition(paneWidth - diver.getFrameWidth());
        }

        if (diver.getYPosition() < 0) {
            diver.setYPosition(0);
        } else if (diver.getYPosition() > paneHeight - diver.getFrameHeight()) {
            diver.setYPosition(paneHeight - diver.getFrameHeight());
        }

        spriteImageView.setX(diver.getXPosition());
        spriteImageView.setY(diver.getYPosition());
    }

    private void updateIdleAnimation() {
        if (!isMoving) {
            currentFrame = (currentFrame + 1) % diver.getFrameCount();
            spriteImageView.setViewport(new Rectangle2D(currentFrame * diver.getFrameWidth(), diver.getUpY(), diver.getFrameWidth(), diver.getFrameHeight()));
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
}
