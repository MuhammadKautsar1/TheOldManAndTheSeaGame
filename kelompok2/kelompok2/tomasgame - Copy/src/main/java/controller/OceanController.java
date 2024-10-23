package controller;

import model.Diver;
import model.Fish;
import view.OceanView;

public class OceanController {
    private Diver diver;
    private Fish[] fishes;
    private OceanView view;

    // Konstruktor
    public OceanController(Diver diver, Fish[] fishes, OceanView view) {
        this.diver = diver;
        this.fishes = fishes;
        this.view = view;
    }

    // Metode untuk memulai permainan
    public void startGame(OceanView view1) {
        boolean playing = true;

        while (playing) {
            // Tampilkan posisi diver dan jumlah ikan yang ditangkap
            view.showDiverPosition(diver.getXPosition(), diver.getYPosition());
            view.showCaughtFishCount(diver.getCaughtFish());

            // Pindahkan dan tampilkan posisi masing-masing ikan
            for (Fish fish : fishes) {
                fish.move();
                view.showFishPosition(fish);
            }

            // Ambil input pengguna
            String input = view.getUserInput().toLowerCase();

            // Tangani pergerakan diagonal
            boolean moveUp = input.contains("w");
            boolean moveDown = input.contains("s");
            boolean moveLeft = input.contains("a");
            boolean moveRight = input.contains("d");

            // Logika pergerakan diver
            if (moveUp && moveRight && diver.canMoveUp() && diver.canMoveRight()) {
                diver.moveUp();
                diver.moveRight();
            } else if (moveUp && moveLeft && diver.canMoveUp() && diver.canMoveLeft()) {
                diver.moveUp();
                diver.moveLeft();
            } else if (moveDown && moveRight && diver.canMoveDown() && diver.canMoveRight()) {
                diver.moveDown();
                diver.moveRight();
            } else if (moveDown && moveLeft && diver.canMoveDown() && diver.canMoveLeft()) {
                diver.moveDown();
                diver.moveLeft();
            } else if (moveUp && diver.canMoveUp()) {
                diver.moveUp();
            } else if (moveDown && diver.canMoveDown()) {
                diver.moveDown();
            } else if (moveLeft && diver.canMoveLeft()) {
                diver.moveLeft();
            } else if (moveRight && diver.canMoveRight()) {
                diver.moveRight();
            } 
            // Menangkap ikan
            else if (input.contains("c")) {
                boolean fishCaught = false;
                for (Fish fish : fishes) {
                    if (diver.isNear(fish)) {
                        diver.catchFish(fish);
                        fishCaught = true;
                        System.out.println("Ikan ditangkap: " + fish.getName());
                    }
                }
                if (!fishCaught) {
                    System.out.println("Ikan terlalu jauh untuk ditangkap!");
                }
            } 
            // Keluar dari permainan
            else if (input.contains("q")) {
                playing = false;
            } else {
                System.out.println("Perintah tidak valid");
            }
        }

        System.out.println("Permainan berakhir!");
    }

    public void startGame() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
