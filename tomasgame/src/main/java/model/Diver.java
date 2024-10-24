package model;

public class Diver extends User{
    private int xPosition;
    private int yPosition;
    private int caughtFish;
    private int catchRadius; // Jarak tangkap ikan (proximity)
    private int moveStep = 10; // Langkah tiap kali bergerak
    private Environment environment; // Referensi ke Environment untuk batas X dan Y

    // Constructor
    public Diver(String uname, String pass, int xPosition, int yPosition, int catchRadius, Environment environment) {
        super(uname, pass);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.caughtFish = 0;
        this.catchRadius = catchRadius; 
        this.environment = environment; 
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
    public void catchFish(MarineLife fish) {
    // Calculate the difference between the diver's and fish's X and Y positions
    int deltaX = Math.abs(fish.getXPosition() - xPosition);
    int deltaY = Math.abs(fish.getYPosition() - yPosition);

    // Check if the fish is within the catch radius on both axes
    if (deltaX <= catchRadius && deltaY <= catchRadius) {
        caughtFish++;
        System.out.println("Ikan tertangkap: " + fish.getName());
    } 
    
}


    // Methods untuk menggerakkan penyelam (dengan batasan environment)
    public void moveUp() {
        if (yPosition + moveStep <= environment.getMaxY()) {
            yPosition += moveStep;
        } 
    }

    public void moveDown() {
        if (yPosition - moveStep >= environment.getMinY()) {
            yPosition -= moveStep;
        } 
    }

    public void moveLeft() {
        if (xPosition - moveStep >= environment.getMinX()) {
            xPosition -= moveStep;
        }
    }

    public void moveRight() {
        if (xPosition + moveStep <= environment.getMaxX()) {
            xPosition += moveStep;
        } 
    }
    
        
    public boolean isNear(MarineLife fish) {
    // Menghitung selisih posisi X dan Y
    int deltaX = Math.abs(fish.getXPosition() - this.xPosition);
    int deltaY = Math.abs(fish.getYPosition() - this.yPosition);
    return deltaX <= catchRadius && deltaY <= catchRadius;
}

}
