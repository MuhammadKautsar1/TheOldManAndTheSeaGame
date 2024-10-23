/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author M Haidar Ali
 */
import java.util.Scanner;
import model.Fish;

public class OceanView {
    private Scanner scanner;

    public OceanView() {
        scanner = new Scanner(System.in);
    }

    public void showDiverPosition(int x, int y) {
        System.out.println("Posisi penyelam: (" + x + ", " + y + ")");
    }

    public void showCaughtFishCount(int count) {
        System.out.println("Jumlah ikan yang tertangkap: " + count);
    }

    public String getUserInput() {
        System.out.println("Masukkan perintah (w/a/s/d untuk bergerak, c untuk menangkap ikan, q untuk keluar): ");
        return scanner.nextLine();
    }

    public void showFishPosition(Fish fish) {
        System.out.println("Ikan " + fish.getName() + " berada di posisi (" + fish.getXPosition() + ", " + fish.getYPosition() + ")");
    }
}
