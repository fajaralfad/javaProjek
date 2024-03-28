package sistemlogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.util.Optional;
import javafx.stage.Stage;

public class PembayaranController {

    @FXML
    private Label welcomeLabel;

    private String loggedInUsername;

    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
        welcomeLabel.setText("Welcome " + username);
    }

    @FXML
    private Button confirmColdplay;

    @FXML
    void actionColdplay(ActionEvent event) {
        showConfirmationDialog("Konfirmasi Pembayaran Coldplay", "Apakah Anda yakin ingin mengkonfirmasi pembayaran Coldplay?");
    }

    private void showConfirmationDialog(String title, String contentText) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(title);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(contentText);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Proses konfirmasi pembayaran (tambahkan logika sesuai kebutuhan Anda)
            showInfoDialog("Konfirmasi Pembayaran", "Pembayaran berhasil dikonfirmasi.");

            // Contoh menggunakan metode initData
            initData("Ringkasan Pembayaran", 100000, "123456");
        } else {
            // Pembayaran tidak dikonfirmasi
            showInfoDialog("Konfirmasi Pembayaran", "Pembayaran tidak dikonfirmasi.");
        }
    }

    private void showInfoDialog(String title, String contentText) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(contentText);
        infoAlert.showAndWait();
    }

    // Metode initData untuk mengirimkan data ke controller
    public void initData(String ringkasanPesanan, int totalBiaya, String nomorUnik) {
        // Lakukan sesuatu dengan data yang diterima (contohnya, tampilkan di label atau lakukan operasi lain)
        System.out.println("Ringkasan: " + ringkasanPesanan);
        System.out.println("Total Biaya: " + totalBiaya);
        System.out.println("Nomor Unik: " + nomorUnik);
    }
    

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // ...
}


