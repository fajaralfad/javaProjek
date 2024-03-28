package sistemlogin;

import DB.DbReg;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXMLLoader;

public class RegisterController {
    
    @FXML
    private JFXTextField reqEmail;

    @FXML
    private JFXTextField reqAlamat;

    @FXML
    private JFXTextField reqNama;

    @FXML
    private JFXTextField regUsername;

    @FXML
    private JFXPasswordField regPassword;

    @FXML
    private Button btnReg;

    private Stage stage; // Referensi ke Stage untuk mengelola Scene
    private Runnable registrationCallback;

    @FXML
    void SendLogin(ActionEvent event) {
        if (event.getSource() == btnReg) {
            insertRecord();
        }
    }

    private void update(String query) {
        try (Connection conn = DbReg.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insertRecord() {
        String username = regUsername.getText();
        String password = regPassword.getText();
        
        if (!username.isEmpty() && !password.isEmpty()) {
            String query = "INSERT INTO `login` VALUES ('" + username + "' , '" + password + "')";
            update(query);

            showInfoMessage("Registration Successful");

            // Panggil callback jika registrasi berhasil
            if (registrationCallback != null) {
                registrationCallback.run();
            }
        } else {
            showErrorMessage("Please fill in both username and password");
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRegistrationCallback(Runnable callback) {
        this.registrationCallback = callback;
    }

    private void navigateToLoginPage() {
        try {
            // Load laman login dari file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            // Set controller untuk laman login
            LogController loginController = loader.getController();

            // Set Stage dan Scene untuk laman login
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Tampilkan laman login
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
