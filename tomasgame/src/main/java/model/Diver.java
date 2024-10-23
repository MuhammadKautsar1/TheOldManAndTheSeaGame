/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author M Haidar Ali
 */
public class Diver {
    private int xPosition;
    private int yPosition;
    private int caughtFish;

    public Diver(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.caughtFish = 0;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getCaughtFish() {
        return caughtFish;
    }

    public void catchFish(Fish fish) {
        if (fish.getXPosition() == xPosition && fish.getYPosition() == yPosition) {
            caughtFish++;
            System.out.println("Ikan tertangkap: " + fish.getName());
        } else {
            System.out.println("Ikan terlalu jauh!");
        }
    }

    public void moveUp() {
        yPosition--;
    }

    public void moveDown() {
        yPosition++;
    }

    public void moveLeft() {
        xPosition--;
    }

    public void moveRight() {
        xPosition++;
    }
}
