package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
        System.out.println("Navigate to Patients");
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
