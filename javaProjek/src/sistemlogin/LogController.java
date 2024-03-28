package sistemlogin;

import DB.DbReg;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogController {

    @FXML
    private JFXTextField fieldUser;

    @FXML
    private JFXPasswordField fieldPass;

    @FXML
    private Button btnLogin;

    @FXML
    private Button insertRegis;

    private Stage stage; // Referensi ke Stage untuk mengelola Scene

    @FXML
    void sendData(ActionEvent event) {
        String username = fieldUser.getText();
        String password = fieldPass.getText();

        if (isValidLogin(username, password)) {
            // Implementasi logika setelah login sukses
            showInfoMessage("Login successful for user: " + username);

            // Menutup window login
            Stage loginStage = (Stage) btnLogin.getScene().getWindow();
            loginStage.close();

            // Membuka window JelajahiAcara.fxml
            openTicketWindow();
        } else {
            // Tampilkan pesan kesalahan jika login gagal
            showErrorMessage("Invalid username or password");
        }
    }

    private void openTicketWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Ticket.fxml"));
            Parent root = loader.load();
            Stage ticketStage = new Stage();
            Scene scene = new Scene(root);
            ticketStage.setScene(scene);

            // Set controller untuk halaman Tiket
            TicketController ticketController = loader.getController();
            ticketController.setLoggedInUsername(fieldUser.getText());

            ticketStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Error loading Ticket.fxml");
        }
    }

    
    @FXML
void sendReg(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        Parent root = loader.load();

        RegisterController registerController = loader.getController();
        
        // Gunakan referensi Stage dari LoginController
        registerController.setStage(stage);

        Scene scene = new Scene(root);
        Stage registerStage = new Stage();
        registerStage.setScene(scene);

        // Set callback untuk menangani aksi setelah registrasi berhasil
        registerController.setRegistrationCallback(() -> {
            registerStage.close();
            navigateToMainPage();
        });

        registerStage.show();
    } catch (IOException e) {
        e.printStackTrace();
        showErrorMessage("Error loading Register.fxml");
    }
}


    private boolean isValidLogin(String username, String password) {
        try (Connection connection = DbReg.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM login WHERE username = ? AND password = ?")) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // true jika ada baris hasil (username dan password sesuai)
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Error checking login");
            return false;
        }
    }

    private void navigateToMainPage() {
        // Implementasi navigasi ke halaman utama
        // ...
    }

    private void showInfoMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
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
