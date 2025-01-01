package controller;

import model.Diver;
import model.MarineLife;
import model.Environment;
import view.OceanView;
import dao.MarineLifeDAO;
import java.util.List;

public class OceanController {
    private Diver diver;
    private MarineLife[] fishes;
    private OceanView view;
    private Environment environment;

    // Constructor
    public OceanController(Diver diver, int numberOfFishes, int fishSpeed, OceanView view, Environment environment) {
        this.diver = diver;
        this.view = view;
        this.environment = environment;

        // Inisialisasi marinelife dari db
        List<MarineLife> marineLifeFromDb = MarineLifeDAO.getAllMarineLife();
        this.fishes = new MarineLife[marineLifeFromDb.size()];

        for (int i = 0; i < marineLifeFromDb.size(); i++) {
            MarineLife marineLife = marineLifeFromDb.get(i);
            this.fishes[i] = new MarineLife(
                marineLife.getId(),
                marineLife.getName(),
                marineLife.getClassification(),
                marineLife.getPoints(),
                marineLife.getDescription(),
                fishSpeed,
                environment
            );
        }

    }

    private void displayFishNames() {
        System.out.println("Fish available in the ocean:");
        for (MarineLife fish : fishes) {
            System.out.println(fish.getName());
        }
    }

    public void startGame() {
        boolean playing = true;

        while (playing) {
            // posisi diver
            view.showDiverPosition(diver.getXPosition(), diver.getYPosition());
            view.showCaughtFishCount(diver.getCaughtFish());

            // gerak biota
            for (MarineLife fish : fishes) {
                fish.move();
                view.showFishPosition(fish);
            }

            // user input
            String input = view.getUserInput().toLowerCase();

            // movement input
            boolean moveUp = input.contains("w");
            boolean moveDown = input.contains("s");
            boolean moveLeft = input.contains("a");
            boolean moveRight = input.contains("d");

            // gerakan diver
            handleMovement(moveUp, moveDown, moveLeft, moveRight);

            // 'c' untuk menangkap ikan
            if (input.contains("c")) {
                boolean caughtFish = false;
                for (MarineLife lifes : fishes) {
                    if (diver.isNear(lifes)) {
                        diver.catchFish(lifes);
                        caughtFish = true;
                        break; 
                    }
                }
                if (!caughtFish) {
                    System.out.println("Biota terlalu jauh");
                }
            } 
            // 'q' untuk keluar
            else if (input.contains("q")) {
                playing = false;
            } 
        }

        System.out.println("Game over!");
    }
    
    private void moveDiver(boolean canMoveFirst, boolean canMoveSecond, Runnable moveFirst, Runnable moveSecond) {
        if (canMoveFirst && canMoveSecond) {
            moveFirst.run();
            moveSecond.run();
        }
    }
    
    // Method untuk gerakan diver berdasarkan input
    private void handleMovement(boolean moveUp, boolean moveDown, boolean moveLeft, boolean moveRight) {
    if (moveUp && moveRight) {
        if (canMoveUp() && canMoveRight()) {
            diver.moveUp();
            diver.moveRight();
        } else {
            if (!canMoveUp()) {
                System.out.println("Diver tidak bisa bergerak lebih ke atas!");
            }
            if (!canMoveRight()) {
                System.out.println("Diver tidak bisa bergerak lebih ke kanan!");
            }
        }
    } else if (moveUp && moveLeft) {
        if (canMoveUp() && canMoveLeft()) {
            diver.moveUp();
            diver.moveLeft();
        } else {
            if (!canMoveUp()) {
                System.out.println("Diver tidak bisa bergerak lebih ke atas!");
            }
            if (!canMoveLeft()) {
                System.out.println("Diver tidak bisa bergerak lebih ke kiri!");
            }
        }
    } else if (moveDown && moveRight) {
        if (canMoveDown() && canMoveRight()) {
            diver.moveDown();
            diver.moveRight();
        } else {
            if (!canMoveDown()) {
                System.out.println("Diver tidak bisa bergerak lebih ke bawah!");
            }
            if (!canMoveRight()) {
                System.out.println("Diver tidak bisa bergerak lebih ke kanan!");
            }
        }
    } else if (moveDown && moveLeft) {
        if (canMoveDown() && canMoveLeft()) {
            diver.moveDown();
            diver.moveLeft();
        } else {
            if (!canMoveDown()) {
                System.out.println("Diver tidak bisa bergerak lebih ke bawah!");
            }
            if (!canMoveLeft()) {
                System.out.println("Diver tidak bisa bergerak lebih ke kiri!");
            }
        }
    } else if (moveUp) {
        if (canMoveUp()) {
            diver.moveUp();
        } else {
            System.out.println("Diver tidak bisa bergerak lebih ke atas!");
        }
    } else if (moveDown) {
        if (canMoveDown()) {
            diver.moveDown();
        } else {
            System.out.println("Diver tidak bisa bergerak lebih ke bawah!");
        }
    } else if (moveLeft) {
        if (canMoveLeft()) {
            diver.moveLeft();
        } else {
            System.out.println("Diver tidak bisa bergerak lebih ke kiri!");
        }
    } else if (moveRight) {
        if (canMoveRight()) {
            diver.moveRight();
        } else {
            System.out.println("Diver tidak bisa bergerak lebih ke kanan!");
        }
    }
}

    
    // Method-method untuk cek apakah diver sudah berada di batas permainan
    private boolean canMoveUp() {
        return diver.getYPosition() < environment.getMaxY();
    }

    private boolean canMoveDown() {
        return diver.getYPosition() > 0;
    }

    private boolean canMoveLeft() {
        return diver.getXPosition() > 0;
    }

    private boolean canMoveRight() {
        return diver.getXPosition() < environment.getMaxX();
    }
}
