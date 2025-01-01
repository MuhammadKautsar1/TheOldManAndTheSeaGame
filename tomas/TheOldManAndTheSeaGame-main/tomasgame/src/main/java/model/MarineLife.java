package model;

import java.util.Random;

public class MarineLife {
    private int id; // Tambahkan ID untuk setiap objek ikan
    private String name;
    private String classification;
    private int points;
    private String description;
    private int xPosition;
    private int yPosition;
    private int directionX; // Arah horizontal (1 ke kanan, -1 ke kiri)
    private int directionY; // Arah vertikal (1 ke bawah, -1 ke atas)
    private int speed; // Kecepatan ikan
    private Environment environment; // Referensi ke lingkungan
    private Random rand; // Generator angka acak untuk gerakan

    // Constructor untuk menghubungkan ikan dengan environment dan spawn di posisi acak
    public MarineLife(int id, String name, String classification, int points, String description, int speed, Environment environment) {
        this.id = id;
        this.name = name;
        this.classification = classification;
        this.points = points;
        this.description = description;
        this.speed = speed;
        this.environment = environment;
        this.rand = new Random();

        // Spawn ikan di posisi acak dalam batas lingkungan
        this.xPosition = rand.nextInt(environment.getMaxX() + 1); 
        this.yPosition = rand.nextInt(environment.getMaxY() + 1); 

        // Arah random, bisa ke kiri (-1) atau ke kanan (1), dan atas (-1) atau bawah (1)
        this.directionX = rand.nextBoolean() ? 1 : -1;
        this.directionY = rand.nextBoolean() ? 1 : -1;
    }

    // Constructor tanpa Environment & speed
    public MarineLife(int id, String name, String species, int points, String description) {
        this.id = id;
        this.name = name;
        this.classification = species;
        this.points = points;
        this.description = description;
    }

    // Getters and Setters
    public int getId() {
        return id;
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

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Method untuk pergerakan ikan dalam lingkungan
    public void move() {
        // gerakan random pada sumbu X/Y
        boolean moveX = rand.nextBoolean(); // true funtuk X, false untuk Y

        if (moveX) {
            int newXPosition = xPosition + directionX * speed;

            // cek batas
            if (newXPosition < environment.getMinX() || newXPosition > environment.getMaxX()) {
                directionX = -directionX; 
            } else {
                xPosition = newXPosition; 
            }
        } else {
            // Gerak pada sumbu Y
            int newYPosition = yPosition + directionY * speed;
            
            //cek batas layar game
            if (newYPosition < environment.getMinY() || newYPosition > environment.getMaxY()) {
                directionY = -directionY;
            } else {
                yPosition = newYPosition; 
            }
        }
    }
}
