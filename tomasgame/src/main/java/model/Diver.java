package model;

public class Diver {
    private int xPosition;
    private int yPosition;
    private int caughtFish;
    private int catchRadius; // Jarak tangkap ikan (proximity)
    private int maxX; // Batas maksimal X pada layar/game
    private int maxY; // Batas maksimal Y pada layar/game
    private int moveStep = 10; // Langkah tiap kali bergerak

    // Constructor
    public Diver(int xPosition, int par1, int par2, int par3, int par4) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.caughtFish = 0;
        this.catchRadius = catchRadius; // Menetapkan radius tangkap
        this.maxX = maxX; // Batas X
        this.maxY = maxY; // Batas Y
    }

    public Diver(String tom, int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getters
    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getCaughtFish() {
        return caughtFish;
    }

    // Method untuk menangkap ikan jika berada dalam jarak tertentu (proximity)
    public void catchFish(Fish fish) {
        // Hitung jarak antara penyelam dan ikan
        double distance = Math.sqrt(Math.pow(fish.getXPosition() - xPosition, 2) + Math.pow(fish.getYPosition() - yPosition, 2));
        
        // Jika jarak antara penyelam dan ikan lebih kecil atau sama dengan radius tangkap, ikan tertangkap
        if (distance <= catchRadius) {
            caughtFish++;
            System.out.println("Ikan tertangkap: " + fish.getName());
        } else {
            System.out.println("Ikan terlalu jauh! Jarak: " + (int) distance + " unit");
        }
    }

    // Methods untuk menggerakkan penyelam
    public void moveUp() {
        yPosition = Math.min(yPosition + moveStep, maxY); // Tidak boleh melebihi batas Y
    }

    public void moveDown() {
        yPosition = Math.max(yPosition - moveStep, 0); // Tidak boleh kurang dari 0 (batas bawah)
    }

    public void moveLeft() {
        xPosition = Math.max(xPosition - moveStep, 0); // Tidak boleh kurang dari 0 (batas kiri)
    }

    public void moveRight() {
        xPosition = Math.min(xPosition + moveStep, maxX); // Tidak boleh melebihi batas X
    }

    // Method tambahan untuk memeriksa apakah penyelam bisa bergerak
    public boolean canMoveUp() {
        return yPosition + moveStep <= maxY;
    }

    public boolean canMoveDown() {
        return yPosition - moveStep >= 0;
    }

    public boolean canMoveLeft() {
        return xPosition - moveStep >= 0;
    }

    public boolean canMoveRight() {
        return xPosition + moveStep <= maxX;
    }

    // Method untuk memeriksa apakah ikan berada dalam radius tangkap
    public boolean isNear(Fish fish) {
        double distance = Math.sqrt(Math.pow(fish.getXPosition() - xPosition, 2) + Math.pow(fish.getYPosition() - yPosition, 2));
        return distance <= catchRadius;
    }
}