/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

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
        Diver diver = new Diver(0, 0);

        // Membuat ikan-ikan dengan posisi acak
        Fish[] fishes = {
            new Fish("Ikan Nemo"),
            new Fish("Ikan Dori"),
            new Fish("Ikan Hiu")
        };

        // Membuat view
        OceanView view = new OceanView();

        // Membuat controller dan menjalankan permainan
        OceanController controller = new OceanController(diver, fishes, view);
        controller.startGame();
    }
}
