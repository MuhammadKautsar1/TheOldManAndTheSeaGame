/*package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import dao.ImageDAO;

public class Fish {

    private ImageView sprite; 

    private static final int FRAME_WIDTH = 90; // Lebar frame sprite
    private static final int FRAME_HEIGHT = 96; // Tinggi frame sprite
    private int LEFT_Y = 0; // Posisi Y untuk animasi ke kiri
    private int RIGHT_Y = LEFT_Y + 96; // Posisi Y untuk animasi ke kanan
    private static final int FRAME_COUNT = 2; // Jumlah frame dalam satu baris animasi

    private Timeline animation;
    private int currentFrame = 2;
    private int UP_Y;
    private int DOWN_Y;
    private Image spriteImage;

    public Fish(Image spriteSheet, int leftY, int rightY) {
    this.sprite = new ImageView(spriteSheet); // Assuming spriteImage is already defined in your Fish class
    this.sprite = new ImageView(spriteSheet);
    this.sprite.setViewport(new javafx.geometry.Rectangle2D(0, 0, FRAME_WIDTH, FRAME_HEIGHT));
    setupAnimation();
    this.LEFT_Y = leftY;
    this.RIGHT_Y = rightY;
}

    public Fish(Image spriteSheet) {
        this.sprite = new ImageView(spriteSheet);
        this.sprite.setViewport(new javafx.geometry.Rectangle2D(0, 0, FRAME_WIDTH, FRAME_HEIGHT));
        setupAnimation();
    }

    private void setupAnimation() {
        animation = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            updateFrame();
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateFrame() {
        int x = currentFrame * FRAME_WIDTH; // Posisi X frame saat ini
        this.sprite.setViewport(new javafx.geometry.Rectangle2D(x, LEFT_Y, FRAME_WIDTH, FRAME_HEIGHT));
        currentFrame = (currentFrame + 1) % FRAME_COUNT; // Berpindah ke frame berikutnya
    }

    public void startAnimation() {
    animation.play();
    }

    public void stopAnimation() {
    animation.stop();
    }

    public ImageView getSprite() {
    return sprite;
    }

    public Image getImage() {
    return sprite.getImage();
    }
        
    public int getFrameWidth() {
    return FRAME_WIDTH;
    }

    public int getFrameHeight() {
    return FRAME_HEIGHT;
    }
}*/
