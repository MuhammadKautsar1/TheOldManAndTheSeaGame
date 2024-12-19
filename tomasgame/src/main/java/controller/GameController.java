package controller;

import dao.ImageDAO;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Diver;
import model.User;

public class GameController implements Initializable {

    @FXML
    private ImageView spriteImageView;
    
    @FXML
    private AnchorPane gamePane; 

    private Diver diver; 
    private static final int FRAME_WIDTH = 128;
    private static final int FRAME_HEIGHT = 128;
    private static final int UP_Y = 0;
    private static final int DOWN_Y = 384;
    private static final int LEFT_Y = 512;
    private static final int RIGHT_Y = 128;
    private static final int FRAME_COUNT = 12;
    private static final int ANIMATION_DURATION = 130;
    private static final int IDLE_ANIMATION_DURATION = 130;

    private int currentFrame = 0;
    private int currentDirectionY = 0; 
    private boolean isMoving = false;

    private Timeline animationTimeline;
    private Timeline idleAnimationTimeline;

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
        spriteImageView.setViewport(new Rectangle2D(0, 0, FRAME_WIDTH, FRAME_HEIGHT));
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
        // Update posisi diver berdasarkan kecepatan dan status gerakan
        if (isMoving) {
            currentFrame = (currentFrame + 1) % FRAME_COUNT;

            // Menentukan model animasi berdasarkan arah gerakan
            if (diver.getRightPressed()) {
                currentDirectionY = RIGHT_Y;  
            } else if (diver.getLeftPressed()) {
                currentDirectionY = LEFT_Y;   
            } else if (diver.getUpPressed()) {
                currentDirectionY = UP_Y;     
            } else if (diver.getDownPressed()) {
                currentDirectionY = DOWN_Y;   
            }

            spriteImageView.setViewport(new Rectangle2D(currentFrame * FRAME_WIDTH, currentDirectionY, FRAME_WIDTH, FRAME_HEIGHT));
        }

        // Environmet permainan berdasarkan ukuran gamePane
        double paneWidth = spriteImageView.getScene().getWidth();
        double paneHeight = spriteImageView.getScene().getHeight();

        diver.updatePosition(paneWidth, paneHeight);

        // Membatasi posisi diver agar tidak keluar dari batas gamePane
        if (diver.getXPosition() < 0) {
            diver.setXPosition(0);  
        } else if (diver.getXPosition() > paneWidth - FRAME_WIDTH) {
            diver.setXPosition(paneWidth - FRAME_WIDTH); 
        }

        if (diver.getYPosition() < 0) {
            diver.setYPosition(0);  
        } else if (diver.getYPosition() > paneHeight - FRAME_HEIGHT) {
            diver.setYPosition(paneHeight - FRAME_HEIGHT);  
        }

        spriteImageView.setX(diver.getXPosition());
        spriteImageView.setY(diver.getYPosition());
    }

    private void updateIdleAnimation() {
        if (!isMoving) {
            currentFrame = (currentFrame + 1) % FRAME_COUNT;
            spriteImageView.setViewport(new Rectangle2D(currentFrame * FRAME_WIDTH, UP_Y, FRAME_WIDTH, FRAME_HEIGHT));
        }
    }
}
