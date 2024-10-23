package com.mycompany.tomasgame;

import java.util.Scanner;
import controller.OceanController;
import model.Diver;
import model.Fish;
import view.OceanView;

class Menu {
    private Scanner scanner = new Scanner(System.in);

    // Menu utama (Sign Up, Sign In, Keluar)
    public void mainMenu(UserManager userManager) {
        int option;
        do {
            System.out.println("\n\t\tSELAMAT DATANG");
            System.out.println("\tPROGRAM BY KELOMPOK 5");
            System.out.println("=======================================================");
            System.out.println("\t||\t1. Sign Up\t||");
            System.out.println("\t||\t2. Sign In\t||");
            System.out.println("\t||\t3. Keluar\t||");
            System.out.println("=======================================================");
            System.out.print("Pilih opsi (1-3): ");
            option = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (option) {
                case 1:
                    handleSignUp(userManager);
                    break;
                case 2:
                    handleSignIn(userManager);
                    break;
                case 3:
                    System.out.println("Keluar dari program.");
                    break;
                default:
                    System.out.println("Opsi tidak valid.");
            }
        } while (option != 3);
    }

    // Menu setelah berhasil sign in
    private void showMainMenu() {
        int choice;
        do {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Start Game");
            System.out.println("2. Book");
            System.out.println("3. History");
            System.out.println("4. Leaderboard");
            System.out.println("5. Quit");
            System.out.print("Pilih opsi (1-5): ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (choice) {
                case 1:
                    System.out.println("Game dimulai!");
                    break;
                case 2:
                    System.out.println("Book terpilih.");
                    break;
                case 3:
                    System.out.println("Menampilkan history.");
                    break;
                case 4:
                    System.out.println("Menampilkan leaderboard.");
                    break;
                case 5:
                    System.out.println("Keluar dari menu utama.");
                    break;
                default:
                    System.out.println("Opsi tidak valid.");
            }
        } while (choice != 5);
    }

    // Fungsi untuk menangani proses Sign Up
    private void handleSignUp(UserManager userManager) {
        System.out.println("\n--- Sign Up ---");
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = inputPassword();

        if (userManager.signUp(username, password)) {
            System.out.println("Sign Up berhasil!");
        } else {
            System.out.println("Username sudah digunakan. Silakan coba username lain.");
        }
    }

    // Fungsi untuk menangani proses Sign In
    private void handleSignIn(UserManager userManager) {
        System.out.println("\n--- Sign In ---");
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = inputPassword();

        if (userManager.signIn(username, password)) {
            System.out.println("\nSign in berhasil!");
            showMainMenu(); // Menampilkan menu utama setelah sign in
        } else {
            System.out.println("Username atau password salah!");
        }
    }

    // Fungsi untuk menangkap input password dengan karakter bintang
    private String inputPassword() {
        StringBuilder password = new StringBuilder();
        char ch;
        while (true) {
            ch = getPasswordChar();
            if (ch == '\n') { // Tekan Enter untuk menyelesaikan input
                break;
            } else if (ch == '\b' && password.length() > 0) { // Backspace untuk menghapus karakter
                System.out.print("\b \b");
                password.deleteCharAt(password.length() - 1);
            } else {
                System.out.print("*");
                password.append(ch);
            }
        }
        System.out.println(); // Untuk pindah baris setelah input selesai
        return password.toString();
    }

    // Fungsi untuk mendapatkan input karakter tanpa menampilkannya di layar (hanya di terminal/console)
    private char getPasswordChar() {
        try {
            return (char) System.in.read();
        } catch (Exception e) {
            return '\n';
        }
    }

    //void mainMenu(UserManager userManager) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    //}
}