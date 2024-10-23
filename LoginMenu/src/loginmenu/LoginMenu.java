package loginmenu;

import java.util.HashMap;
import java.util.Scanner;

public class LoginMenu {
    static HashMap<String, String> users = new HashMap<>(); // Menyimpan username dan password
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int option;
        
        do {
            // Menampilkan menu utama
            System.out.println("\n\t\tSELAMAT DATANG");
            System.out.println("        \tPROGRAM BY KELOMPOK 2");
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
                    signUp();
                    break;
                case 2:
                    signIn();
                    break;
                case 3:
                    System.out.println("Keluar dari program.");
                    break;
                default:
                    System.out.println("Opsi tidak valid.");
            }
        } while (option != 3);

        scanner.close();
    }

    // Fitur Sign Up
    public static void signUp() {
        System.out.println("\n--- Sign Up ---");
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();

        // Memastikan username belum digunakan
        if (users.containsKey(username)) {
            System.out.println("Username sudah digunakan. Silakan coba username lain.");
            return;
        }

        System.out.print("Masukkan password: ");
        String password = inputPassword();

        // Menyimpan username dan password
        users.put(username, password);
        System.out.println("Sign Up berhasil!");
    }

    // Fitur Sign In
    public static void signIn() {
        System.out.println("\n--- Sign In ---");
        System.out.print("Masukkan username: ");
        String inputUsername = scanner.nextLine();

        System.out.print("Masukkan password: ");
        String inputPassword = inputPassword();

        // Verifikasi username dan password
        if (users.containsKey(inputUsername) && users.get(inputUsername).equals(inputPassword)) {
            System.out.println("\nSign in berhasil!");
            showMainMenu();
        } else {
            System.out.println("Username atau password salah!");
        }
    }

    // Menampilkan menu setelah berhasil Sign In
    public static void showMainMenu() {
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
                    // Logika untuk memulai game bisa ditambahkan di sini
                    break;
                case 2:
                    System.out.println("Book terpilih.");
                    // Logika untuk fitur book bisa ditambahkan di sini
                    break;
                case 3:
                    System.out.println("Menampilkan history.");
                    // Logika untuk menampilkan history bisa ditambahkan di sini
                    break;
                case 4:
                    System.out.println("Menampilkan leaderboard.");
                    // Logika untuk menampilkan leaderboard bisa ditambahkan di sini
                    break;
                case 5:
                    System.out.println("Keluar dari menu utama.");
                    break;
                default:
                    System.out.println("Opsi tidak valid.");
            }
        } while (choice != 5);
    }

    // Fungsi untuk menangkap input password dengan bintang
    public static String inputPassword() {
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
        System.out.println();
        return password.toString();
    }

    // Fungsi untuk mendapatkan input karakter tanpa menampilkannya di layar (untuk terminal/console)
    public static char getPasswordChar() {
        try {
            return (char) System.in.read();
        } catch (Exception e) {
            return '\n';
        }
    }
}
