package model;

public class Environment {
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    // Constructor using the gamePane's dimensions
    public Environment(double minX, double maxX, double minY, double maxY) {
        this.minX = (int) minX;
        this.maxX = (int) maxX;
        this.minY = (int) minY;
        this.maxY = (int) maxY;
    }

    // Getters
    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }
}
