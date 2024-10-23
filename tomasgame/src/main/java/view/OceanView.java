package view;

import model.Fish;
import java.util.Scanner;

public class OceanView {
    private Scanner scanner;

    public OceanView() {
        scanner = new Scanner(System.in);
    }

    public void showDiverPosition(int x, int y) {
        System.out.println("Posisi penyelam: (" + x + ", " + y + ")");
    }

    public void showCaughtFishCount(int count) {
        System.out.println("Jumlah ikan yang ditangkap: " + count);
    }

    public void showFishPosition(Fish fish) {
        if (!fish.isCaught()) {
            System.out.println("Posisi ikan: (" + fish.getXPosition() + ", " + fish.getYPosition() + ")");
        }
    }

    public String getUserInput() {
        System.out.print("Masukkan perintah (w/a/s/d untuk bergerak, c untuk menangkap ikan, q untuk keluar): ");
        return scanner.nextLine();
    }
}
