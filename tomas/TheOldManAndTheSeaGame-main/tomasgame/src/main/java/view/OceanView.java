package view;

import java.util.Scanner;
import model.MarineLife;

public class OceanView {
    private Scanner scanner;

    public OceanView() {
        scanner = new Scanner(System.in);
    }

    public void showDiverPosition(double x, double y) {
        System.out.println("Posisi penyelam: (" + x + ", " + y + ")");
    }

    public void showCaughtFishCount(int count) {
        System.out.println("Jumlah ikan yang tertangkap: " + count);
    }

    public String getUserInput() {
        System.out.println("Masukkan perintah (w/a/s/d untuk bergerak, c untuk menangkap ikan, q untuk keluar): ");
        return scanner.nextLine();
    }

    public void showFishPosition(MarineLife lifes) {
    if (lifes.getName() != null) {
        System.out.println(lifes.getName() + " berada di posisi (" + lifes.getXPosition() + ", " + lifes.getYPosition() + ")");
        } 
    }
}
