package model;

public class Environment {
    private int maxX; 
    private int maxY;
    private int minY = 0; 
    private int minX = 0; 

    // Constructor untuk menginisialisasi ukuran lingkungan permainan
    public Environment(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    // Getters
    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }
}
