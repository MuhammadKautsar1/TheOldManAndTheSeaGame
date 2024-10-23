
package model;

import java.util.Random;

public class Fish {
    private String name;
    private boolean caught;
    private int xPosition;
    private int yPosition;
    private int directionX; // arah horizontal (1 ke kanan, -1 ke kiri)
    private int directionY; // arah vertikal (1 ke bawah, -1 ke atas)
    private int speed; // kecepatan ikan
    private static final int screenWidth = 100; // lebar layar (contoh 100)
    private static final int screenHeight = 100; // tinggi layar (contoh 100)
    private Random rand; // Generator angka acak untuk gerakan

    // Constructor
    public Fish(String name, int speed) {
        this.name = name;
        this.rand = new Random();
        // Spawn ikan di posisi acak, misalnya dalam area -10 sampai 10 untuk x dan y
        this.xPosition = rand.nextInt(21) - 10; // random dari -10 hingga 10
        this.yPosition = rand.nextInt(21) - 10; // random dari -10 hingga 10
        this.speed = speed;

        // Arah random, bisa ke kiri (-1) atau ke kanan (1), dan atas (-1) atau bawah (1)
        this.directionX = rand.nextBoolean() ? 1 : -1;
        this.directionY = rand.nextBoolean() ? 1 : -1;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    // Method untuk pergerakan ikan
    public void move() {
        // Randomly decide whether to move on X axis or Y axis
        boolean moveX = rand.nextBoolean(); // true for X movement, false for Y movement

        if (moveX) {
            // Move on X axis
            xPosition += directionX * speed;

            // Bounce back if it hits the screen edges horizontally
            if (xPosition < 0 || xPosition > screenWidth) {
                directionX = -directionX; // Membalikkan arah horizontal
            }
        } else {
            // Move on Y axis
            yPosition += directionY * speed;

            // Bounce back if it hits the screen edges vertically
            if (yPosition < 0 || yPosition > screenHeight) {
                directionY = -directionY; // Membalikkan arah vertikal
            }
        }
    }

    public boolean isCaught() {
        return caught;
    }
}