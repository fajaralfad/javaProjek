package sistemlogin;

import DB.DbReg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class TicketController {
    @FXML
    private Button payment;

    @FXML
    private Button TiketAvengers;

    @FXML
    private Button TiketHungerGames;

    @FXML
    private Button TiketGampangCuan;

    @FXML
    private Button TiketShangchi;

    @FXML
    private Button TiketGatotKaca;

    @FXML
    private Button TiketKkn;

    @FXML
    private Label welcomeLabel;

    private String loggedInUsername;

    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
        welcomeLabel.setText("Welcome " + username);
    }

    @FXML
    void BeliAvengers(ActionEvent event) {
        int jumlahTiket = 1;
        handleBeliTiket("Coldplay", "Senin, 26 Januari 2024", "Rp. 1.500.000", generateNomorUnik(), jumlahTiket);
        handleBeliTiket("Coldplay", "Selasa, 27 Januari 2024", "Rp. 1.700.000", generateNomorUnik(), jumlahTiket);
        handleBeliTiket("Coldplay", "Senin, 29 Januari 2024", "Rp. 2.500.000", generateNomorUnik(), jumlahTiket);
    }

    @FXML
    void BeliShangchi(ActionEvent event) {
        int jumlahTiket = 1;
        handleBeliTiket("Festival Musik", "Selasa, 26 Agustus 2024", "Rp. 55.000", generateNomorUnik(), jumlahTiket);
        handleBeliTiket("Festival Musik", "Rabu, 27 Agustus 2024", "Rp. 70.000", generateNomorUnik(), jumlahTiket);
        handleBeliTiket("Festival Musik", "Kamis, 28 Agustus 2024", "Rp. 90.000", generateNomorUnik(), jumlahTiket);
    }

    @FXML
    void beliGampangCuan(ActionEvent event) {
        int jumlahTiket = 1;
        handleBeliTiket("Ed sheeran", "Sabtu, 2 Maret 2024", "Rp. 10.000.000", generateNomorUnik(), jumlahTiket);
        handleBeliTiket("Ed sheeran", "Minggu, 3 Maret 2024", "Rp. 11.000.000", generateNomorUnik(), jumlahTiket);
        handleBeliTiket("Ed sheeran", "Senin, 4 Maret 2024", "Rp. 9.000.000", generateNomorUnik(), jumlahTiket);
    }

    @FXML
    void beliGatotKaca(ActionEvent event) {
        int jumlahTiket = 1;
        handleBeliTiket("Tiket Pesawat", "Sabtu, 1 Januari 2024", "Rp. 4.000.000", generateNomorUnik(), jumlahTiket);
        handleBeliTiket("Tiket Pesawat", "Minggu, 2 Januari 2024", "Rp. 6.000.000", generateNomorUnik(), jumlahTiket);
        handleBeliTiket("Tiket Pesawat", "Senin, 3 Januari 2024", "Rp. 3.000.000", generateNomorUnik(), jumlahTiket);
    }

    @FXML
    void beliHungerGames(ActionEvent event) {
        int jumlahTiket = 1;
        handleBeliTiket("Tiket Kereta", "Sabtu, 1 Januari 2024", "Rp. 20.000", generateNomorUnik(), jumlahTiket);
        handleBeliTiket("Tiket Kereta", "Minggu, 2 Januari 2024", "Rp. 35.000", generateNomorUnik(), jumlahTiket);
        handleBeliTiket("Tiket Kereta", "Senin, 3 Januari 2024", "Rp. 15.000", generateNomorUnik(), jumlahTiket);
    }

    @FXML
    void beliKkn(ActionEvent event) {
        int jumlahTiket = 1;
        handleBeliTiket("Tiket Bus", "Sabtu, 1 Januari 2024", "Rp. 600.000", generateNomorUnik(), jumlahTiket);
        handleBeliTiket("Tiket Bus", "Minggu, 2 Januari 2024", "Rp. 650.000", generateNomorUnik(), jumlahTiket);
        handleBeliTiket("Tiket Bus", "Senin, 3 Januari 2024", "Rp. 450.000", generateNomorUnik(), jumlahTiket);
    }

    @FXML
    void goPembayaran(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Pembayaran.fxml"));
        Parent root = loader.load();

        // Mendapatkan referensi controller dari loader
        PembayaranController pembayaranController = loader.getController();

        // Menetapkan stage dari controller sebelum menampilkannya
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        pembayaranController.setStage(currentStage);

        Scene scene = new Scene(root);
        Stage pembayaranStage = new Stage();
        pembayaranStage.setScene(scene);

        // Menutup stage saat ini sebelum menampilkan stage pembayaran
        currentStage.close();

        pembayaranStage.show();
    } catch (IOException e) {
        e.printStackTrace();
        showErrorMessage("Error loading Pembayaran.fxml");
    }
}

    

    private String getRingkasanPesanan(String film, String jadwal, String harga, int jumlahTiket) {
        // Sesuaikan dengan format ringkasan pesanan Anda
        return String.format("Film: %s\nJadwal: %s\nHarga: %s\nJumlah Tiket: %d", film, jadwal, harga, jumlahTiket);
    }

    private int calculateTotalBiaya(String harga, int jumlahTiket) {
        // Sesuaikan dengan logika perhitungan total biaya Anda
        return Integer.parseInt(harga.replace("Rp. ", "").replace(".", "")) * jumlahTiket;
    }

    private void handleBeliTiket(String film, String jadwal, String harga, String nomorUnik, int jumlahTiket) {
        String message = String.format("Informasi Tiket:\nFilm: %s\nJadwal: %s\nHarga: %s\nJumlah Tiket: %d\nNomor Unik: %s",
                film, jadwal, harga, jumlahTiket, nomorUnik);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi Tiket");
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType beliButton = new ButtonType("Beli Tiket");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(beliButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == beliButton) {
            simpanDataPembelianTiket(film, jadwal, harga, jumlahTiket, nomorUnik);
            showInfoMessage("Tiket berhasil dipesan, silakan konfirmasi pembayaran terlebih dahulu!");
        }
    }

    private void simpanDataPembelianTiket(String film, String jadwal, String harga, int jumlahTiket, String nomorUnik) {
        try (Connection connection = DbReg.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO pembelian_tiket (film, jadwal, harga, jumlah_tiket, nomor_unik) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, film);
            preparedStatement.setString(2, jadwal);
            preparedStatement.setString(3, harga);
            preparedStatement.setInt(4, jumlahTiket);
            preparedStatement.setString(5, nomorUnik);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Error saving ticket purchase data to database");
        }
    }

    private String generateNomorUnik() {
        return Long.toString(System.currentTimeMillis());
    }

    private void showInfoMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
                alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
