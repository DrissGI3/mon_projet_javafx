package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SecretaryController {

    @FXML
    private Button btnCreateAppointment;

    @FXML
    private Button btnEditAppointment;

    @FXML
    private Button btnViewSchedule;

    @FXML
    private Button btnViewAvailability;

    @FXML
    private Button btnPatients; // Top bar

    @FXML
    private Button btnAdmin; // Top bar

    @FXML
    private Button btnDoctors; // Top bar

    @FXML
    public void initialize() {
        // Initialize dashboard data if needed
    }

    @FXML
    private void handleCreateAppointment() {
        System.out.println("Create Appointment Clicked");
        // Logic to open create appointment dialog/view
    }

    @FXML
    private void handleEditAppointment() {
        System.out.println("Edit Appointment Clicked");
        // Logic to open edit/cancel view
    }

    @FXML
    private void handleViewSchedule() {
        System.out.println("View Schedule Clicked");
        // Logic to switch to schedule view
    }

    @FXML
    private void handleViewAvailability() {
        System.out.println("View Availability Clicked");
        // Logic to show availability
    }

    @FXML
    private void handleManagePatients() {
        try {
            System.out.println("Navigate to Patients");

            // Get current stage
            Stage stage = (Stage) btnPatients.getScene().getWindow();

            // Load patient FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/patient.fxml"));
            Scene scene = new Scene(loader.load());

            // Apply stylesheet
            scene.getStylesheets().add(getClass().getResource("/css/patient.css").toExternalForm());

            // Set scene
            stage.setScene(scene);
            stage.setTitle("ClinicCare - Gestion des Patients");

        } catch (Exception e) {
            System.err.println("Error navigating to Patient Interface: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdministrative() {
        System.out.println("Navigate to Admin");
    }

    @FXML
    private void handleDoctors() {
        System.out.println("Navigate to Doctors");
    }
}
