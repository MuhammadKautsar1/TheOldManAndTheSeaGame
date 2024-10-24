package com.mycompany.tomasgame;

import controller.OceanController;
import controller.Menu;
import model.Diver;
import model.Environment;
import view.OceanView;


public class Tomasgame {
    public static void main(String[] args) {
        // Untuk membuat atau menginisialisasi Ukuran Tempat Bermain
        Environment environment = new Environment(100, 100);

        // Menginisialisasi objek diver
        Diver diver = new Diver("DiverName", "DiverID", 50, 50, 10, environment);

        // Menginisialisasi objek ocean view
        OceanView view = new OceanView();
        
        // Menginisialisasi controller game
        OceanController controller = new OceanController(diver, 3, 5, view, environment);

        // Menginisialisasi Menu sebagai Main Controller dan menampilkanya
        Menu menu = new Menu(controller);
        menu.displayMainMenu(); 
    }
}
