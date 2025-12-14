package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    private void initialize() {
        roleBox.getItems().addAll(
                "Admin",
                "Medecin",
                "Infermier",
                "Comptable"
        );
    }

    @FXML
    private void onLogin(ActionEvent event) {
        String role = roleBox.getValue();

        if (role == null) {
            showAlert("Erreur", "Veuillez choisir un rôle");
            return;
        }

        try {
            if (role.equals("Admin")) {
                // Charger l'interface Admin
                Parent root = FXMLLoader.load(
                        getClass().getResource("/fxml/admin.fxml")
                );

                Stage stage = (Stage) usernameField
                        .getScene()
                        .getWindow();

                stage.setScene(new Scene(root));
                stage.setTitle("Admin");
                stage.show();

            } else {
                showAlert("Info",
                        "Interface " + role + " pas encore créée");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
