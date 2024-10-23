/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Diver;
import model.Fish;
import view.OceanView;

/**
 *
 * @author M Haidar Ali
 */
// OceanController.java (Controller)
public class OceanController {
    private Diver diver;
    private Fish[] fishes;
    private OceanView view;

    public OceanController(Diver diver, Fish[] fishes, OceanView view) {
        this.diver = diver;
        this.fishes = fishes;
        this.view = view;
    }

    public void startGame() {
        boolean playing = true;

        while (playing) {
            view.showDiverPosition(diver.getXPosition(), diver.getYPosition());
            view.showCaughtFishCount(diver.getCaughtFish());

            for (Fish fish : fishes) {
                fish.move(); // ikan bergerak
                view.showFishPosition(fish);
            }

            String input = view.getUserInput();

            switch (input) {
                case "w": diver.moveUp(); break;
                case "a": diver.moveLeft(); break;
                case "s": diver.moveDown(); break;
                case "d": diver.moveRight(); break;
                case "c":
                    for (Fish fish : fishes) {
                        diver.catchFish(fish);
                    }
                    break;
                case "q": playing = false; break;
                default: System.out.println("Perintah tidak valid");
            }
        }
        System.out.println("Permainan berakhir!");
    }
}

