/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author M Haidar Ali
 */

   import java.util.Random;

public class Fish {
    private String name;
    private int xPosition;
    private int yPosition;

    public Fish(String name) {
        this.name = name;
        Random rand = new Random();
        // Spawn ikan di posisi acak, misal dalam area -10 sampai 10 untuk x dan y
        this.xPosition = rand.nextInt(21) - 10; // random dari -10 hingga 10
        this.yPosition = rand.nextInt(21) - 10; // random dari -10 hingga 10
    }

    public String getName() {
        return name;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void move() {
        // Logika pergerakan ikan (misalnya, ikan bergerak ke arah random)
        xPosition += (int)(Math.random() * 10 - 5); // bergerak acak
        yPosition += (int)(Math.random() * 10 - 5); // bergerak acak
    }
}
