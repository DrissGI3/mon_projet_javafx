package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Session;

public class DoctorController {

    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize() {
        if (Session.getUsername() != null) {
            welcomeLabel.setText("Bienvenue, Dr. " + Session.getUsername());
        }
    }

    @FXML
    private void handlePatientsTab() {
        switchScene("/fxml/patient.fxml", "Gestion Patients");
    }

    @FXML
    private void handleRendezVousTab() {
        switchScene("/fxml/appointment.fxml", "Gestion Rendez-vous");
    }

    @FXML
    private void handleTraitementsTab() {
        switchScene("/fxml/treatment.fxml", "Gestion Traitements");
    }

    @FXML
    private void handleLogout() {
        Session.clear();
        switchScene("/fxml/login.fxml", "Login");
    }

    private void switchScene(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            welcomeLabel.getScene().setRoot(root);
            ((Stage) welcomeLabel.getScene().getWindow()).setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
