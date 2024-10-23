package com.mycompany.tomasgame;

import controller.OceanController;
import model.Diver;
import model.Fish;
import view.OceanView;

public class Tomasgame {
    public static void main(String[] args) {
        UserManager userManager = new UserManager(); // Mengelola pengguna
        Menu menu = new Menu(); // Menampilkan berbagai menu

        // Menampilkan menu utama
        menu.mainMenu(userManager);

        // Membuat penyelam
        Diver diver = new Diver("Tom", 0);

        // Membuat ikan-ikan dengan posisi acak
        Fish[] fishes = {
            new Fish("Ikan Nemo", 50),
            new Fish("Ikan Dori", 50),
            new Fish("Ikan Hiu", 50)
        };

        // Membuat view
        OceanView view = new OceanView();

        // Membuat controller dan menjalankan permainan
        OceanController oceanController = new OceanController(diver, fishes, view);
        oceanController.startGame(); // Memulai permainan
    }
}
