package model;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MarineLife {
    // Core attributes (from database)
    private int id;
    private String name;
    private String classification;
    private int points;
    private String description;
    private int idImage; // Foreign key for image

    // Runtime attributes (game logic)
    private int xPosition;
    private int yPosition;
    private int directionX;
    private int directionY;
    private int speed;
    private int stepsRemaining = 45; // Jumlah langkah tersisa dalam satu arah
    private Environment environment;
    private Random rand;

    // Animation-related attributes
    private ImageView sprite;
    private static final int FRAME_WIDTH = 90;
    private static final int FRAME_HEIGHT = 96;
    private static final int FRAME_COUNT = 2; // Jumlah frame per animasi
    private static final int LEFT_Y = 96; // Y untuk animasi ke kiri
    private static final int RIGHT_Y = 0; // Y untuk animasi ke kanan
    private Timeline animation;
    private int currentFrame = 0;
    private boolean isOutOfBounds = false;

    // Constructors
    public MarineLife(int id, String name, String classification, int points, String description, int idImage) {
        this.id = id;
        this.name = name;
        this.classification = classification;
        this.points = points;
        this.description = description;
        this.idImage = idImage;
    }

    public MarineLife(int id, String name, String classification, int points, String description, int speed, Environment environment, Image spriteSheet) {
        this(id, name, classification, points, description, 0);
        this.speed = speed;
        this.environment = environment;
        this.rand = new Random();

        // Initialize sprite and animation
        this.sprite = new ImageView(spriteSheet);
        this.sprite.setViewport(new Rectangle2D(0, RIGHT_Y, FRAME_WIDTH, FRAME_HEIGHT)); // Default ke kanan
        setupAnimation();
    }

    // Initialize position and direction
    public void initializePositionAndDirection() {
        boolean spawnAtMinX = rand.nextBoolean(); // Randomize spawn at minX or maxX
        if (spawnAtMinX) {
            this.xPosition = environment.getMinX(); // Spawn at minX
            this.directionX = 1; // Move right
            this.sprite.setViewport(new Rectangle2D(0, RIGHT_Y, FRAME_WIDTH, FRAME_HEIGHT)); // Menghadap kanan
        } else {
            this.xPosition = environment.getMaxX(); // Spawn at maxX
            this.directionX = -1; // Move left
            this.sprite.setViewport(new Rectangle2D(0, LEFT_Y, FRAME_WIDTH, FRAME_HEIGHT)); // Menghadap kiri
        }

        // Randomize Y position within bounds
        this.yPosition = rand.nextInt(environment.getMaxY() - environment.getMinY() + 1) + environment.getMinY();

        // Randomize vertical direction
        this.directionY = rand.nextBoolean() ? 1 : -1;
    }

    // Movement logic
    public void move() {
    // Horizontal movement
    int newXPosition = xPosition + directionX * speed;

    // Check horizontal bounds
    if (newXPosition < environment.getMinX() - FRAME_WIDTH || newXPosition > environment.getMaxX() + FRAME_WIDTH) {
        isOutOfBounds = true;
        return; // Stop movement if out of bounds
    }
    xPosition = newXPosition;

    // Vertical movement
    if (stepsRemaining > 0) {
        int newYPosition = yPosition + directionY * speed;

        // Check vertical bounds
        if (newYPosition < environment.getMinY()) {
            directionY = 1; // Change direction down
            newYPosition = environment.getMinY();
        } else if (newYPosition > environment.getMaxY() - FRAME_HEIGHT) {
            directionY = -1; // Change direction up
            newYPosition = environment.getMaxY() - FRAME_HEIGHT;
        }

        yPosition = newYPosition;
        stepsRemaining--;
    } else {
        // Reset steps and randomize vertical direction
        stepsRemaining = 45;
        directionY = rand.nextBoolean() ? 1 : -1;
    }

    // Update the sprite's direction based on horizontal movement
    if (directionX == 1) {
        this.sprite.setViewport(new Rectangle2D(currentFrame * FRAME_WIDTH, RIGHT_Y, FRAME_WIDTH, FRAME_HEIGHT)); // Menghadap kanan
    } else if (directionX == -1) {
        this.sprite.setViewport(new Rectangle2D(currentFrame * FRAME_WIDTH, LEFT_Y, FRAME_WIDTH, FRAME_HEIGHT)); // Menghadap kiri
    }
}


    // Animation setup
    private void setupAnimation() {
        animation = new Timeline(new KeyFrame(Duration.millis(100), event -> updateFrame()));
        animation.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateFrame() {
        currentFrame = (currentFrame + 1) % FRAME_COUNT;
        int x = currentFrame * FRAME_WIDTH;
        int y = (directionX == 1) ? RIGHT_Y : LEFT_Y; // Tentukan Y berdasarkan arah
        this.sprite.setViewport(new Rectangle2D(x, y, FRAME_WIDTH, FRAME_HEIGHT));
    }

    public void startAnimation() {
        animation.play();
    }

    public void stopAnimation() {
        animation.stop();
    }

    // Getters and setters
    public boolean isOutOfBounds() {
        return isOutOfBounds;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }
}
