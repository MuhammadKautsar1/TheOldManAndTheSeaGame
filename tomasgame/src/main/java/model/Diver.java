package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Diver extends User {
    private double xPosition;
    private double yPosition;
    private int caughtFish;
    private int catchRadius;
    private double HP = 100.0;

    private double velocityX = 0;
    private double velocityY = 0;
    private static final double PLAYER_SPEED = 2.0;
    private static final double FRICTION = 0.8;
    public static final double MIN_VELOCITY = 0.1;

    private ImageView sprite; 

    private static final int FRAME_WIDTH = 128;
    private static final int FRAME_HEIGHT = 128;
    private static final int UP_Y = 0;
    private static final int DOWN_Y = 384;
    private static final int LEFT_Y = 512;
    private static final int RIGHT_Y = 128;
    private static final int FRAME_COUNT = 12;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public Diver(User user, double xPosition, double yPosition, int catchRadius, Image spriteImage) {
        super(user.getUid(), user.getUname(), user.getPass());
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.caughtFish = 0;
        this.catchRadius = catchRadius;
        this.sprite = new ImageView(spriteImage);
        this.sprite.setFitWidth(catchRadius * 2); 
        this.sprite.setFitHeight(catchRadius * 2);
        this.sprite.setX(this.xPosition);
        this.sprite.setY(this.yPosition);
    }

    public boolean getUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean getDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean getLeftPressed() {
        return leftPressed;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean getRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void moveUp() { 
        velocityY -= PLAYER_SPEED; 
    }
    public void moveDown() { 
        velocityY += PLAYER_SPEED; 
    }
    public void moveLeft() { 
        velocityX -= PLAYER_SPEED; 
    }
    public void moveRight() { 
        velocityX += PLAYER_SPEED;
    }

    public void updatePosition(double paneWidth, double paneHeight) {
        if (getUpPressed()) velocityY -= PLAYER_SPEED; 
        if (getDownPressed()) velocityY += PLAYER_SPEED;
        if (getLeftPressed()) velocityX -= PLAYER_SPEED;
        if (getRightPressed()) velocityX += PLAYER_SPEED;

        if (!getUpPressed() && !getDownPressed()) velocityY *= FRICTION;
        if (!getLeftPressed() && !getRightPressed()) velocityX *= FRICTION;

        if (Math.abs(velocityX) < MIN_VELOCITY) velocityX = 0;
        if (Math.abs(velocityY) < MIN_VELOCITY) velocityY = 0;

        xPosition += velocityX;
        yPosition += velocityY;

        if (xPosition < 0) xPosition = 0;
        if (xPosition > paneWidth - FRAME_WIDTH) xPosition = paneWidth - FRAME_WIDTH;
        if (yPosition < 0) yPosition = 0;
        if (yPosition > paneHeight - FRAME_HEIGHT) yPosition = paneHeight - FRAME_HEIGHT;

        sprite.setX(xPosition);
        sprite.setY(yPosition);
    }

    public Image getImage() {
        return sprite.getImage();
    }

    public ImageView getSprite() {
        return sprite;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

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

    public int getCaughtFish() { 
        return caughtFish; 
    }
    public void setCaughtFish(int caughtFish) {
        this.caughtFish = caughtFish; 
    }

    public int getCatchRadius() { return catchRadius; }
    public void setCatchRadius(int catchRadius) { 
        this.catchRadius = catchRadius;
        sprite.setFitWidth(catchRadius * 2);
        sprite.setFitHeight(catchRadius * 2);
    }

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
}
