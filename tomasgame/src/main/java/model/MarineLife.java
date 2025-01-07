package model;

import dao.ImageDAO;
import java.io.ByteArrayInputStream;
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
    private int idImage;
    private Image spriteImage;

    // Runtime attributes (game logic)
    private int xPosition;
    private int yPosition;
    private int directionX;
    private int directionY;
    private int speed;
    private int stepsRemaining = 45; // Steps remaining in one direction
    private Environment environment;
    private Random rand;

    // Animation-related attributes
    private ImageView sprite;
    private static final int FRAME_WIDTH = 90;
    private static final int FRAME_HEIGHT = 96;
    private static final int FRAME_COUNT = 2; // Number of frames per animation
    private static final int LEFT_Y = 96; // Y for left animation
    private static final int RIGHT_Y = 0; // Y for right animation
    private Timeline animation;
    private int currentFrame = 0;
    private boolean isOutOfBounds = false;
    private boolean isStaticMode = false; // New flag to control movement behavior

    // Constructors
    public MarineLife(int id, String name, String classification, int points, String description, int idImage) {
        this.id = id;
        this.name = name;
        this.classification = classification != null ? classification : "Unknown";
        this.points = points;
        this.description = description;
        this.idImage = idImage;
        initializeSprite();
    }

    public MarineLife(int id, String name, String classification, int points, String description, int speed, Environment environment, Image spriteSheet) {
        this(id, name, classification, points, description, 0);
        this.speed = speed;
        this.environment = environment;
        this.spriteImage = spriteSheet;
        this.rand = new Random();

        if (spriteSheet != null) {
            this.sprite = new ImageView(spriteSheet);
            this.sprite.setViewport(new Rectangle2D(0, RIGHT_Y, FRAME_WIDTH, FRAME_HEIGHT));
            setupAnimation();
        }
    }

    // Initialize position and direction for game
    public void initializePositionAndDirection() {
        if (!isStaticMode) {
            boolean spawnAtMinX = rand.nextBoolean();
            if (spawnAtMinX) {
                this.xPosition = environment.getMinX();
                this.directionX = 1;
                this.sprite.setViewport(new Rectangle2D(0, RIGHT_Y, FRAME_WIDTH, FRAME_HEIGHT));
            } else {
                this.xPosition = environment.getMaxX();
                this.directionX = -1;
                this.sprite.setViewport(new Rectangle2D(0, LEFT_Y, FRAME_WIDTH, FRAME_HEIGHT));
            }

            this.yPosition = rand.nextInt(environment.getMaxY() - environment.getMinY() + 1) + environment.getMinY();
            this.directionY = rand.nextBoolean() ? 1 : -1;
        }
    }

    // Movement logic for game
    public void move() {
        if (isStaticMode) return; // Skip movement if in static mode

        int newXPosition = xPosition + directionX * speed;

        if (newXPosition < environment.getMinX() - FRAME_WIDTH || newXPosition > environment.getMaxX() + FRAME_WIDTH) {
            isOutOfBounds = true;
            return;
        }
        xPosition = newXPosition;

        if (stepsRemaining > 0) {
            int newYPosition = yPosition + directionY * speed;

            if (newYPosition < environment.getMinY()) {
                directionY = 1;
                newYPosition = environment.getMinY();
            } else if (newYPosition > environment.getMaxY() - FRAME_HEIGHT) {
                directionY = -1;
                newYPosition = environment.getMaxY() - FRAME_HEIGHT;
            }

            yPosition = newYPosition;
            stepsRemaining--;
        } else {
            stepsRemaining = 45;
            directionY = rand.nextBoolean() ? 1 : -1;
        }

        updateSpriteDirection();
    }

    // New methods for static positioning
    public void setStaticMode(boolean staticMode) {
        this.isStaticMode = staticMode;
    }

    public void centerInPane(double paneWidth, double paneHeight) {
        if (sprite != null) {
            sprite.setLayoutX((paneWidth - sprite.getFitWidth()) / 2);
            sprite.setLayoutY((paneHeight - sprite.getFitHeight()) / 2);
        }
    }

    // Sprite initialization and management
    private void initializeSprite() {
        if (sprite == null) {
            Image image = getImage();
            if (image != null) {
                sprite = new ImageView(image);
                sprite.setViewport(new Rectangle2D(0, RIGHT_Y, FRAME_WIDTH, FRAME_HEIGHT));
                sprite.setFitWidth(FRAME_WIDTH);
                sprite.setFitHeight(FRAME_HEIGHT);
                setupAnimation();
            }
        }
    }

    private void updateSpriteDirection() {
        if (directionX == 1) {
            sprite.setViewport(new Rectangle2D(currentFrame * FRAME_WIDTH, RIGHT_Y, FRAME_WIDTH, FRAME_HEIGHT));
        } else {
            sprite.setViewport(new Rectangle2D(currentFrame * FRAME_WIDTH, LEFT_Y, FRAME_WIDTH, FRAME_HEIGHT));
        }
    }

    // Animation setup and control
    private void setupAnimation() {
        animation = new Timeline(new KeyFrame(Duration.millis(200), event -> updateFrame()));
        animation.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateFrame() {
        currentFrame = (currentFrame + 1) % FRAME_COUNT;
        if (isStaticMode) {
            // In static mode, always face right
            sprite.setViewport(new Rectangle2D(currentFrame * FRAME_WIDTH, RIGHT_Y, FRAME_WIDTH, FRAME_HEIGHT));
        } else {
            updateSpriteDirection();
        }
    }

    public void startAnimation() {
        if (animation != null) {
            animation.play();
        }
    }

    public void stopAnimation() {
        if (animation != null) {
            animation.stop();
        }
    }

    // Image loading
    public Image getImage() {
        if (spriteImage == null && idImage > 0) {
            byte[] imageData = ImageDAO.getImageById(idImage);
            if (imageData != null) {
                spriteImage = new Image(new ByteArrayInputStream(imageData));
            }
        }
        return spriteImage;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getClassification() { return classification; }
    public void setClassification(String classification) { this.classification = classification; }
    public int getPoints() { return points; }
    public String getDescription() { return description; }
    public int getIdImage() { return idImage; }
    public void setIdImage(int idImage) { this.idImage = idImage; }
    public ImageView getSprite() { return sprite; }
    public int getXPosition() { return xPosition; }
    public void setXPosition(int xPosition) { this.xPosition = xPosition; }
    public int getYPosition() { return yPosition; }
    public void setYPosition(int yPosition) { this.yPosition = yPosition; }
    public Environment getEnvironment() { return environment; }
    public void setEnvironment(Environment environment) { this.environment = environment; }
    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }
    public Random getRand() { return rand; }
    public void setRand(Random rand) { this.rand = rand; }
    public boolean isOutOfBounds() { return isOutOfBounds; }
    
    public void changeDirection() {
        directionX *= -1;
        updateSpriteDirection();
    }
}