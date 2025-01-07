package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Diver extends User {
    private double xPosition;
    private double yPosition;
    private int caughtFish = 0;
    private int catchRadius = 50;
    private double HP = 100.0;
    private int score = 0;

    private double velocityX = 0;
    private double velocityY = 0;
    private static final double PLAYER_SPEED = 2.0;
    private static final double FRICTION = 0.8;
    private static final double MIN_VELOCITY = 0.1;

    private ImageView sprite;

    private static final int FRAME_WIDTH = 128;
    private static final int FRAME_HEIGHT = 128;
    private static final int UP_Y = 0;
    private static final int DOWN_Y = 384;
    private static final int LEFT_Y = 512;
    private static final int RIGHT_Y = 128;
    private static final int FRAME_COUNT = 12;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    public Diver(User user, double xPosition, double yPosition, Image spriteImage) {
        super(user.getUid(), user.getUname(), user.getPass());
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.sprite = new ImageView(spriteImage);
        this.sprite.setFitWidth(catchRadius * 2);
        this.sprite.setFitHeight(catchRadius * 2);
        this.sprite.setX(xPosition);
        this.sprite.setY(yPosition);
    }

    // * Movement Controls *
    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    // * Movement Logic *
    public void updatePosition(double paneWidth, double paneHeight) {
        // Update velocity based on key inputs
        if (upPressed) velocityY -= PLAYER_SPEED;
        if (downPressed) velocityY += PLAYER_SPEED;
        if (leftPressed) velocityX -= PLAYER_SPEED;
        if (rightPressed) velocityX += PLAYER_SPEED;

        // Apply friction
        if (!upPressed && !downPressed) velocityY *= FRICTION;
        if (!leftPressed && !rightPressed) velocityX *= FRICTION;

        // Prevent small velocities from causing unnecessary movement
        if (Math.abs(velocityX) < MIN_VELOCITY) velocityX = 0;
        if (Math.abs(velocityY) < MIN_VELOCITY) velocityY = 0;

        // Update positions
        xPosition += velocityX;
        yPosition += velocityY;

        // Keep within bounds
        xPosition = Math.max(0, Math.min(paneWidth - FRAME_WIDTH, xPosition));
        yPosition = Math.max(0, Math.min(paneHeight - FRAME_HEIGHT, yPosition));

        // Update sprite position
        sprite.setX(xPosition);
        sprite.setY(yPosition);
    }

    // * Collision and Game Stats *
    public int getCaughtFish() {
        return caughtFish;
    }

    public User getUser() {
    return new User(getUid(), getUname(), getPass());
}

    public static double getPLAYER_SPEED() {
        return PLAYER_SPEED;
    }

    public static double getFRICTION() {
        return FRICTION;
    }

    public static double getMIN_VELOCITY() {
        return MIN_VELOCITY;
    }

    public static int getFRAME_WIDTH() {
        return FRAME_WIDTH;
    }

    public static int getFRAME_HEIGHT() {
        return FRAME_HEIGHT;
    }

    public static int getUP_Y() {
        return UP_Y;
    }

    public static int getDOWN_Y() {
        return DOWN_Y;
    }

    public static int getLEFT_Y() {
        return LEFT_Y;
    }

    public static int getRIGHT_Y() {
        return RIGHT_Y;
    }

    public static int getFRAME_COUNT() {
        return FRAME_COUNT;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void setSprite(ImageView sprite) {
        this.sprite = sprite;
    }

    
    

    public void setCaughtFish(int caughtFish) {
        this.caughtFish = caughtFish;
    }

    public int getCatchRadius() {
        return catchRadius;
    }

    public void setCatchRadius(int catchRadius) {
        this.catchRadius = catchRadius;
        sprite.setFitWidth(catchRadius * 2);
        sprite.setFitHeight(catchRadius * 2);
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = Math.max(0, Math.min(100, HP)); // Restrict HP between 0 and 100
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    // * Sprite Management *
    public Image getImage() {
        return sprite.getImage();
    }

    public ImageView getSprite() {
        return sprite;
    }

    public void setSpriteImage(Image image) {
        this.sprite.setImage(image);
    }

    // * Animation Properties *
    public int getFrameWidth() {
        return FRAME_WIDTH;
    }

    public int getFrameHeight() {
        return FRAME_HEIGHT;
    }

    public int getUpY() {
        return UP_Y;
    }

    public int getDownY() {
        return DOWN_Y;
    }

    public int getLeftY() {
        return LEFT_Y;
    }

    public int getRightY() {
        return RIGHT_Y;
    }

    public int getFrameCount() {
        return FRAME_COUNT;
    }

    // * Position Getters and Setters *
    public double getXPosition() {
        return xPosition;
    }

    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
        sprite.setX(xPosition);
    }

    public double getYPosition() {
        return yPosition;
    }

    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
        sprite.setY(yPosition);
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getxPosition() {
        return xPosition;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }
    
    
}