package controller;

import dao.UserDAO;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.User;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private OceanController oceanController;
    private Map<String, String> users; // Menyimpan pasangan username-password

    // Constructor
    public Menu(OceanController oceanController) {
        this.oceanController = oceanController;
        this.users = new HashMap<>(); // inisialisasi penyimpanan user
    }

    // Method untuk menampilkan main menu
    public void displayMainMenu() {
        int option;
        do {
            System.out.println("\n\t\tSELAMAT DATANG");
            System.out.println("\tPROGRAM BY KELOMPOK 2");
            System.out.println("=======================================================");
            System.out.println("\t||\t1. Sign Up\t||");
            System.out.println("\t||\t2. Sign In\t||");
            System.out.println("\t||\t3. Keluar\t||");
            System.out.println("=======================================================");
            System.out.print("Pilih opsi (1-3): ");
            option = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (option) {
                case 1:
                    handleSignUp();
                    break;
                case 2:
                    handleSignIn();
                    break;
                case 3:
                    System.out.println("Keluar dari program.");
                    break;
                default:
                    System.out.println("Opsi tidak valid.");
            }
        } while (option != 3);
    }
    
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

            switch (choice) {
                case 1:
                    System.out.println("Game dimulai!");
                    oceanController.startGame();
                    break;
                case 2:
                    System.out.println("Book");
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


    // Method untuk SignUp
private void handleSignUp() {
    System.out.println("\n--- Sign Up ---");
    System.out.print("Masukkan username: ");
    String username = scanner.nextLine();
    System.out.print("Masukkan password: ");
    String password = scanner.nextLine(); 

    if (UserDAO.isUsernameTaken(username)) {
        System.out.println("Username sudah digunakan. Silakan coba username lain.");
    } else {
        // Register the new user
        User newUser = new User(username, password);
        UserDAO.registerUser(newUser);
        System.out.println("Sign Up berhasil!");
    }
}

// Method untuk SignIn
private void handleSignIn() {
    System.out.println("\n--- Sign In ---");
    System.out.print("Masukkan username: ");
    String username = scanner.nextLine();
    System.out.print("Masukkan password: ");
    String password = scanner.nextLine();

    User user = UserDAO.validate(username, password);
    if (user != null) {
        System.out.println("\nSign in berhasil!");
        showMainMenu();
    } else {
        System.out.println("Username atau password salah!");
    }
}

}
