package controller;

import static controller.LoginController.user;
import dao.HistoryDAO;
import dao.ImageDAO;
import dao.MarineLifeDAO;
import dao.UserCatchDAO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Diver;
import model.MarineLife;
import model.User;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Environment;

public class GameController implements Initializable {

    @FXML
    private AnchorPane gamePane1;
    @FXML
    private ImageView diverSprite;
    @FXML
    private Label timerLabel;
    @FXML
    private Button backButton;
    @FXML
    private ImageView marineLifeSprite;
    @FXML
    private Label scoreLabel;
    @FXML
    private ProgressBar healthPoint;
    @FXML
    private ImageView obstacleSprite;
    
    private static final double CATCH_DISTANCE = 50; // Jarak maksimum untuk menangkap marine life
    private MediaPlayer mediaPlayer;

    private Diver diver;
    private static final int ANIMATION_DURATION = 130;
    private static final int IDLE_ANIMATION_DURATION = 130;

    private static final double MAX_WIDTH = 1280; // Batas lebar 1280
    private static final double MAX_HEIGHT = 720; // Batas tinggi 720

    private int currentFrame = 0;
    private int currentDirectionY = 0;
    private boolean isMoving = false;

    private Timeline animationTimeline;
    private Timeline idleAnimationTimeline;
    private Timeline countdownTimer;
    private Timeline marineLifeSpawnTimeline;
    private Timeline marineLifeMovementTimeline;

    private int remainingTimeInSeconds = 60;

    private Random random = new Random();
    private ArrayList<MarineLife> marineLifeList = new ArrayList<>(); // Daftar MarineLife

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    playBackgroundMusic();
    try {
        initializeDiver();
        setupSprite();
        setupKeyboardControls();
        setupAnimation();
        startTimer();
        startMarineLifeSpawning();
        startMarineLifeMovement();
        animateMarineLife(); // Tambahkan animasi
    } catch (SQLException e) {
        e.printStackTrace();
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Initialization Error");
        alert.setHeaderText("Game Initialization Failed");
        alert.setContentText("Failed to initialize game components!");
        alert.showAndWait();
    }
}

    private void initializeDiver() throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages();
        if (images != null && !images.isEmpty()) {
            byte[] imageData = images.get(0);
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));
            User user = new User("1", "Player", "password");
            diver = new Diver(user, 100, 100, spriteImage);
        } else {
            throw new IllegalStateException("No diver sprite found in the database.");
        }
    }

    private void setupSprite() {
        if (diver != null) {
            diverSprite.setImage(diver.getSprite().getImage());
            diverSprite.setViewport(new Rectangle2D(0, 0, diver.getFrameWidth(), diver.getFrameHeight()));
        }
    }

    private void setupKeyboardControls() {
        diverSprite.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Scene scene = newScene;
                scene.setOnKeyPressed(this::handleKeyPressed);
                scene.setOnKeyReleased(this::handleKeyReleased);
            }
        });
    }

    private void handleKeyPressed(KeyEvent event) {
        if (diver == null) return;
        switch (event.getCode()) {
            case W -> diver.setUpPressed(true);
            case S -> diver.setDownPressed(true);
            case A -> diver.setLeftPressed(true);
            case D -> diver.setRightPressed(true);
        }
        isMoving = true;
        idleAnimationTimeline.stop();
    }

    private void handleKeyReleased(KeyEvent event) {
        if (diver == null) return;
        switch (event.getCode()) {
            case W -> diver.setUpPressed(false);
            case S -> diver.setDownPressed(false);
            case A -> diver.setLeftPressed(false);
            case D -> diver.setRightPressed(false);
        }
        if (!diver.isUpPressed() && !diver.isDownPressed() && !diver.isLeftPressed() && !diver.isRightPressed()) {
            isMoving = false;
            idleAnimationTimeline.play();
        }
    }

    private void startMarineLifeMovement() {
        marineLifeMovementTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0.1), e -> updateMarineLifeMovement())
        );
        marineLifeMovementTimeline.setCycleCount(Timeline.INDEFINITE);
        marineLifeMovementTimeline.play();
    }

        private void updateMarineLifeMovement() {
        ArrayList<MarineLife> marineLifeToRemove = new ArrayList<>(); // Daftar untuk menyimpan MarineLife yang akan dihapus

        for (MarineLife marineLife : marineLifeList) {
            marineLife.move(); // Pergerakan sesuai logika di kelas MarineLife

            // Update posisi sprite
            marineLife.getSprite().setLayoutX(marineLife.getXPosition());
            marineLife.getSprite().setLayoutY(marineLife.getYPosition());

            // Cek apakah MarineLife keluar dari batas kiri atau kanan
            if (marineLife.getXPosition() < 0 || marineLife.getXPosition() > MAX_WIDTH) {
                marineLifeToRemove.add(marineLife); // Tambahkan ke daftar untuk dihapus
            }
        }

        // Hapus MarineLife yang keluar dari layar
        for (MarineLife marineLife : marineLifeToRemove) {
            gamePane1.getChildren().remove(marineLife.getSprite()); // Hapus dari gamePane
            marineLifeList.remove(marineLife); // Hapus dari daftar
        }
    }

    private void setupAnimation() {
        animationTimeline = new Timeline(
            new KeyFrame(Duration.millis(ANIMATION_DURATION), e -> updateAnimation())
        );
        animationTimeline.setCycleCount(Timeline.INDEFINITE);
        animationTimeline.play();
        idleAnimationTimeline = new Timeline(
        new KeyFrame(Duration.millis(IDLE_ANIMATION_DURATION), e -> updateIdleAnimation())
        );
        idleAnimationTimeline.setCycleCount(Timeline.INDEFINITE);
        idleAnimationTimeline.play();
    }

    private void updateAnimation() {
        if (diver == null || diverSprite.getScene() == null) return;
        if (isMoving) {
            currentFrame = (currentFrame + 1) % diver.getFrameCount();
            if (diver.isRightPressed()) {
                currentDirectionY = diver.getRightY();
            } else if (diver.isLeftPressed()) {
                currentDirectionY = diver.getLeftY();
            } else if (diver.isUpPressed()) {
                currentDirectionY = diver.getUpY();
            } else if (diver.isDownPressed()) {
                currentDirectionY = diver.getDownY();
            }
            diverSprite.setViewport(new Rectangle2D(currentFrame * diver.getFrameWidth(), currentDirectionY, diver.getFrameWidth(), diver.getFrameHeight()));
        }

        diver.updatePosition(MAX_WIDTH, MAX_HEIGHT);
        ensureInBounds();

        diverSprite.setX(diver.getXPosition());
        diverSprite.setY(diver.getYPosition());

        checkCollisionWithMarineLife();
    }

   private void checkCollisionWithMarineLife() {
    ArrayList<MarineLife> caughtMarineLifeList = new ArrayList<>();

    double diverX = diver.getXPosition();
    double diverY = diver.getYPosition();

    // Iterasi daftar marineLife
    for (MarineLife marineLife : marineLifeList) {
        double distance = Math.sqrt(Math.pow(marineLife.getXPosition() - diverX, 2) +
                                    Math.pow(marineLife.getYPosition() - diverY, 2));

        if (distance <= CATCH_DISTANCE) {
            caughtMarineLifeList.add(marineLife);

            // menyimpan hasil tangkapan ke database
            try {
                UserCatchDAO.addUserCatch(user.getUid(), marineLife.getId());
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Database Error");
                alert.setContentText("Failed to save catch to database!");
                alert.showAndWait();
            }

            // Tambahkan ID marineLife ke sessionCatches
            sessionCatches.add(marineLife.getId());
        }
    }

    // Hapus marinelife yang telah tertangkap dari daftar
    for (MarineLife caught : caughtMarineLifeList) {
        marineLifeList.remove(caught);
        removeCaughtMarineLife(caught);
        }
    }
    private void updateIdleAnimation() {
        if (diver == null || isMoving) return;

        currentFrame = (currentFrame + 1) % diver.getFrameCount();
        diverSprite.setViewport(new Rectangle2D(
            currentFrame * diver.getFrameWidth(),
            diver.getUpY(),
            diver.getFrameWidth(),
            diver.getFrameHeight()
        ));
    }

    private void ensureInBounds() {
        if (diver.getXPosition() < 0) {
            diver.setXPosition(0);
        } else if (diver.getXPosition() > MAX_WIDTH - diver.getFrameWidth()) {
            diver.setXPosition(MAX_WIDTH - diver.getFrameWidth());
        }
        if (diver.getYPosition() < 0) {
            diver.setYPosition(0);
        } else if (diver.getYPosition() > MAX_HEIGHT - diver.getFrameHeight()) {
            diver.setYPosition(MAX_HEIGHT - diver.getFrameHeight());
        }
    }

    private void startTimer() {
        countdownTimer = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> updateTimer())
        );
        countdownTimer.setCycleCount(Timeline.INDEFINITE);
        countdownTimer.play();
    }

    private void updateTimer() {
    if (remainingTimeInSeconds > 0) {
        remainingTimeInSeconds--;
        int minutes = remainingTimeInSeconds / 60;
        int seconds = remainingTimeInSeconds % 60;
        timerLabel.setText(String.format("TIMER: %02d:%02d", minutes, seconds));
    } else {
        countdownTimer.stop();
        timerLabel.setText("Time's up!");
        endGame(); // Akhiri permainan
        }
    }

    private void startMarineLifeSpawning() {
        marineLifeSpawnTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> spawnMarineLife())
        );
        marineLifeSpawnTimeline.setCycleCount(Timeline.INDEFINITE);
        marineLifeSpawnTimeline.play();
    }

    private double getRandomSpawnInterval() {
        return 3 + random.nextDouble() * 5; // Random antara 5 hingga 10 detik
    }

    private void updateSpawnInterval() {
        marineLifeSpawnTimeline.stop(); // Hentikan timeline lama
        marineLifeSpawnTimeline.getKeyFrames().clear(); // Hapus semua KeyFrame lama
        marineLifeSpawnTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(getRandomSpawnInterval()), e -> {
            spawnMarineLife();
            updateSpawnInterval();
        }));
        marineLifeSpawnTimeline.play(); // Jalankan timeline baru
    }

    private MarineLife MarineLife1(double xPosition, double yPosition) throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages();
        if (images != null && !images.isEmpty() && images.size() > 1) {
            byte[] imageData = images.get(1);
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));
            Environment environment = new Environment(0, gamePane1.getWidth(), 0, gamePane1.getHeight());
            MarineLife marineLife = new MarineLife(1, "Marine Life Type 1", "Classification", 10, "Description", 10, environment, spriteImage);
            marineLife.initializePositionAndDirection(); // Initialisasi posisi dan kecepatan arah
            return marineLife;
        } else {
            throw new IllegalStateException("Tidak ada data ikan didata Base");
        }
    }

    private MarineLife MarineLife2(double xPosition, double yPosition) throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages();
        if (images != null && !images.isEmpty() && images.size() > 2) {
            byte[] imageData = images.get(2);
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));
            Environment environment = new Environment(0, gamePane1.getWidth(), 0, gamePane1.getHeight());
            MarineLife marineLife = new MarineLife(2, "Marine Life Type 2", "Classification", 20, "Description", 15, environment, spriteImage);
            marineLife.initializePositionAndDirection(); // Initialisasi posisi dan kecepatan arah
            return marineLife;
        } else {
            throw new IllegalStateException("Tidak ada data ikan didata Base");
        }
    }
    
    private MarineLife MarineLife3(double xPosition, double yPosition) throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages();
        if (images != null && !images.isEmpty() && images.size() > 2) {
            byte[] imageData = images.get(3);
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));
            Environment environment = new Environment(3, gamePane1.getWidth(), 0, gamePane1.getHeight());
            MarineLife marineLife = new MarineLife(3, "Marine Life Type 3", "Classification", 30, "Description", 15, environment, spriteImage);
            marineLife.initializePositionAndDirection(); // Initialisasi posisi dan kecepatan arah
            return marineLife;
        } else {
            throw new IllegalStateException("NTidak ada data ikan didata Base");
        }
    }
    
    private MarineLife MarineLife4(double xPosition, double yPosition) throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages();
        if (images != null && !images.isEmpty() && images.size() > 1) {
            byte[] imageData = images.get(4);
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));
            Environment environment = new Environment(0, gamePane1.getWidth(), 0, gamePane1.getHeight());
            MarineLife marineLife = new MarineLife(4, "Marine Life Type 1", "Classification", 10, "Description", 10, environment, spriteImage);
            marineLife.initializePositionAndDirection(); // Initialize position and movement direction
            return marineLife;
        } else {
            throw new IllegalStateException("Tidak ada data ikan didata Base");
        }
    }
    
    private MarineLife MarineLife5(double xPosition, double yPosition) throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages();
        if (images != null && !images.isEmpty() && images.size() > 1) {
            byte[] imageData = images.get(5);
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));
            Environment environment = new Environment(0, gamePane1.getWidth(), 0, gamePane1.getHeight());
            MarineLife marineLife = new MarineLife(5, "Marine Life Type 1", "Classification", 10, "Description", 10, environment, spriteImage);
            marineLife.initializePositionAndDirection(); // Initialisasi posisi dan kecepatan arah
            return marineLife;
        } else {
            throw new IllegalStateException("Tidak ada data ikan didata Base");
        }
    }
    
    private MarineLife MarineLife6(double xPosition, double yPosition) throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages();
        if (images != null && !images.isEmpty() && images.size() > 1) {
            byte[] imageData = images.get(6);
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));
            Environment environment = new Environment(0, gamePane1.getWidth(), 0, gamePane1.getHeight());
            MarineLife marineLife = new MarineLife(6, "Marine Life Type 1", "Classification", 10, "Description", 10, environment, spriteImage);
            marineLife.initializePositionAndDirection(); // Initialisasi posisi dan kecepatan arah
            return marineLife;
        } else {
            throw new IllegalStateException("Tidak ada data ikan didata Base");
        }
    }
    
    private MarineLife MarineLife7(double xPosition, double yPosition) throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages();
        if (images != null && !images.isEmpty() && images.size() > 1) {
            byte[] imageData = images.get(7);
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));
            Environment environment = new Environment(0, gamePane1.getWidth(), 0, gamePane1.getHeight());
            MarineLife marineLife = new MarineLife(7, "Marine Life Type 1", "Classification", 10, "Description", 10, environment, spriteImage);
            marineLife.initializePositionAndDirection(); // Initialisasi posisi dan kecepatan arah
            return marineLife;
        } else {
            throw new IllegalStateException("Tidak ada data ikan didata Base");
        }
    }
    
    private MarineLife MarineLife8(double xPosition, double yPosition) throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages();
        if (images != null && !images.isEmpty() && images.size() > 1) {
            byte[] imageData = images.get(8);
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));
            Environment environment = new Environment(0, gamePane1.getWidth(), 0, gamePane1.getHeight());
            MarineLife marineLife = new MarineLife(8, "Marine Life Type 1", "Classification", 10, "Description", 10, environment, spriteImage);
            marineLife.initializePositionAndDirection(); // Initialisasi posisi dan kecepatan arah
            return marineLife;
        } else {
            throw new IllegalStateException("Tidak ada data ikan didata Base");
        }
    }
    
    private MarineLife MarineLife9(double xPosition, double yPosition) throws SQLException {
        ArrayList<byte[]> images = ImageDAO.getImages();
        if (images != null && !images.isEmpty() && images.size() > 1) {
            byte[] imageData = images.get(9);
            Image spriteImage = new Image(new ByteArrayInputStream(imageData));
            Environment environment = new Environment(0, gamePane1.getWidth(), 0, gamePane1.getHeight());
            // Create MarineLife object with specific attributes for type 1
            MarineLife marineLife = new MarineLife(9, "Marine Life Type 1", "Classification", 10, "Description", 10, environment, spriteImage);
            marineLife.initializePositionAndDirection(); // Initialisasi posisi dan kecepatan arah

            return marineLife;
        } else {
            throw new IllegalStateException("Tidak ada data ikan didata Base");
        }
    }

    private void spawnMarineLife() {
    try {
        double xPosition = random.nextDouble() * (MAX_WIDTH - 50); 
        double yPosition = random.nextDouble() * (MAX_HEIGHT - 50); 

        MarineLife newMarineLife;
        int randomType = random.nextInt(8); 

        if (randomType == 0) {
            newMarineLife = MarineLife1(xPosition, yPosition); // Spawn MarineLife1
        } else if (randomType == 1) {
            newMarineLife = MarineLife2(xPosition, yPosition); // Spawn MarineLife2
        } else if (randomType == 2){
            newMarineLife = MarineLife3(xPosition, yPosition); // Spawn MarineLife3
        } else if (randomType == 3){
            newMarineLife = MarineLife4(xPosition, yPosition); // Spawn MarineLife4
        } else if (randomType == 4){
            newMarineLife = MarineLife5(xPosition, yPosition); // Spawn MarineLife5
        } else if (randomType == 5){
            newMarineLife = MarineLife6(xPosition, yPosition); // Spawn MarineLife6
        } else if (randomType == 6){
            newMarineLife = MarineLife7(xPosition, yPosition); // Spawn MarineLife7
        } else if (randomType == 7){
            newMarineLife = MarineLife8(xPosition, yPosition); // Spawn MarineLife8
        } else{
            newMarineLife = MarineLife9(xPosition, yPosition); // Spawn MarineLife9
        }
           

        marineLifeList.add(newMarineLife);  
        gamePane1.getChildren().add(newMarineLife.getSprite());
        newMarineLife.startAnimation();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    private MarineLife catchMarineLife() {
            MarineLife caughtMarineLife = null;
            double diverX = diver.getXPosition();
            double diverY = diver.getYPosition();
            for (MarineLife marineLife : marineLifeList) {
                double distance = Math.sqrt(Math.pow(marineLife.getXPosition() - diverX, 2) +
                                            Math.pow(marineLife.getYPosition() - diverY, 2));
                if (distance <= CATCH_DISTANCE) {
                    caughtMarineLife = marineLife;
                    break;
                }
            }
            return caughtMarineLife;
        }

        private void removeCaughtMarineLife(MarineLife marineLife) {
            gamePane1.getChildren().remove(marineLife.getSprite());
        }

        private void animateMarineLife() {
        marineLifeMovementTimeline = new Timeline(
            new KeyFrame(Duration.millis(50), e -> updateMarineLifeSprite())
        );
        marineLifeMovementTimeline.setCycleCount(Timeline.INDEFINITE);
        marineLifeMovementTimeline.play();
    }

    private void updateMarineLifeSprite() {
        if (marineLifeSprite != null) {
            double newX = marineLifeSprite.getLayoutX() + (random.nextDouble() - 0.5) * 10; 
            double newY = marineLifeSprite.getLayoutY() + (random.nextDouble() - 0.5) * 10; 

            // Pastikan sprite tetap dalam batas layar
            if (newX < 0) newX = 0;
            if (newX > MAX_WIDTH - marineLifeSprite.getFitWidth()) newX = MAX_WIDTH - marineLifeSprite.getFitWidth();
            if (newY < 0) newY = 0;
            if (newY > MAX_HEIGHT - marineLifeSprite.getFitHeight()) newY = MAX_HEIGHT - marineLifeSprite.getFitHeight();

            marineLifeSprite.setLayoutX(newX);
            marineLifeSprite.setLayoutY(newY);
        }
    }
    
   @FXML
    public void handleBack(MouseEvent event) {
        stopBackgroundMusic();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Keluar");
        confirmationAlert.setHeaderText("Apakah Anda yakin ingin keluar?");
        confirmationAlert.setContentText("Progres tidak akan ke save");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                URL url = new File("src/main/java/view/MainMenu.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                Stage stage = (Stage) backButton.getScene().getWindow();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Loading Error");
                alert.setHeaderText("Failed to Load Main Menu");
                alert.setContentText("Check your FXML file!");
                alert.showAndWait();
            }
        }
    }

    private void endGame() {
    // Hentikan semua timeline yang sedang berjalan
    if (countdownTimer != null) countdownTimer.stop();
    if (marineLifeMovementTimeline != null) marineLifeMovementTimeline.stop();
    if (marineLifeSpawnTimeline != null) marineLifeSpawnTimeline.stop();
    if (animationTimeline != null) animationTimeline.stop();
    if (idleAnimationTimeline != null) idleAnimationTimeline.stop();

    // Hitung total skor berdasarkan marine life yang ditangkap
    int totalScore = calculateTotalScoreForSession();

    // Simpan skor ke database menggunakan HistoryDAO
    try {
        if (user != null) {
            String level = "EASY"; // Ganti sesuai level permainan
            Timestamp date = new Timestamp(System.currentTimeMillis());
            boolean isSaved = HistoryDAO.addHistory(level, date, totalScore, user.getUid());
            if (isSaved) {
                System.out.println("Game history saved successfully!");
            } else {
                System.out.println("Failed to save game history!");
            }
        } else {
            System.out.println("Pengguna tidak masuk game tidak bisa di save ke histori!");
        }
    } catch (Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Game History Save Failed");
        alert.setContentText("An error occurred while saving game history!");
        alert.showAndWait();
    }

    // Tampilkan popup dengan skor akhir menggunakan Platform.runLater
    Platform.runLater(() -> {
        Alert scoreAlert = new Alert(Alert.AlertType.INFORMATION);
        scoreAlert.setTitle("Game Over");
        scoreAlert.setHeaderText("Waktu Habis!");
        scoreAlert.setContentText("Skor Akhir Anda: " + totalScore);
        scoreAlert.showAndWait();
        stopBackgroundMusic();

        // Kembali ke menu utama setelah dialog ditutup
        try {
            URL url = new File("src/main/java/view/MainMenu.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) gamePane1.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Loading Error");
            alert.setHeaderText("Failed to Load Main Menu");
            alert.setContentText("Check your FXML file!");
            alert.showAndWait();
        }
    });
}
    
// Fungsi untuk menghitung total skor}
private List<Integer> sessionCatches = new ArrayList<>();

public void addUserCatch(int userId, int marineLifeId) {
        // Tambahkan ke database
        UserCatchDAO.addUserCatch(userId, marineLifeId);

        // Simpan di daftar tangkapan sesi
        sessionCatches.add(marineLifeId);
    }

    private int calculateTotalScoreForSession() {
        int totalScore = 0;
        for (int marineLifeId : sessionCatches) {
            totalScore += MarineLifeDAO.getMarineLifePoints(marineLifeId);
        }
        return totalScore;
    }

    public boolean saveGameSession(String level, int userId) {
        int totalScore = calculateTotalScoreForSession();
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());

        // Simpan skor ke tabel history
        return HistoryDAO.addHistory(level, currentDate, totalScore, userId);
    }

    public void resetSession() {
        sessionCatches.clear(); // Reset tangkapan sesi setelah permainan selesai
    }
    
    private void playBackgroundMusic() {
        // Tentukan lokasi file musik
        String musicFile = "src/main/java/assets/backsound.mp3";  // Sesuaikan dengan lokasi file musik Anda
        File musicPath = new File(musicFile);

        if (musicPath.exists()) {
            Media media = new Media(musicPath.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Memutar musik secara terus-menerus
            mediaPlayer.play();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Musik Tidak Ditemukan");
            alert.setContentText("Musik yang Anda cari tidak ditemukan!");
            alert.showAndWait();
        }
    }

    private void stopBackgroundMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();  // musik berhenti
        }
    }
}
