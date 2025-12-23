package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Appointment;
import models.AppointmentDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class AppointmentController {

    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> colId;
    @FXML
    private TableColumn<Appointment, String> colPatient;
    @FXML
    private TableColumn<Appointment, String> colDoctor;
    @FXML
    private TableColumn<Appointment, LocalDate> colDate;
    @FXML
    private TableColumn<Appointment, LocalTime> colTime;
    @FXML
    private TableColumn<Appointment, String> colReason;
    @FXML
    private TableColumn<Appointment, String> colStatus;
    @FXML
    private TableColumn<Appointment, LocalDateTime> colCreatedAt;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadAppointments();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colPatient.setCellValueFactory(cellData -> cellData.getValue().patientNameProperty());
        colDoctor.setCellValueFactory(cellData -> cellData.getValue().doctorNameProperty());
        colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        colTime.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        colReason.setCellValueFactory(cellData -> cellData.getValue().reasonProperty());
        colCreatedAt.setCellValueFactory(cellData -> cellData.getValue().createdAtProperty());
    }

    private void loadAppointments() {
        appointmentList.setAll(AppointmentDAO.getAllAppointments());
        appointmentTable.setItems(appointmentList);
    }

    @FXML
    private void handleStartConsultation() {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Information", "Veuillez sélectionner un rendez-vous.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/consultation.fxml"));
            Parent root = loader.load();

            ConsultationController controller = loader.getController();
            controller.setAppointment(selected);

            Stage stage = new Stage();
            stage.setTitle("Consultation Médicale - " + selected.getPatientName());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(appointmentTable.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadAppointments(); // Refresh status if changed
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir l'interface de consultation.");
        }
    }

    @FXML
    private void handleNewAppointment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add_appointment.fxml"));
            Parent root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nouveau Rendez-vous");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(appointmentTable.getScene().getWindow());
            dialogStage.setScene(new Scene(root));

            AddAppointmentController controller = loader.getController();
            controller.setParentController(this);
            dialogStage.showAndWait();

            if (controller.isSaveClicked()) {
                loadAppointments();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir le formulaire.");
        }
    }

    @FXML
    private void handleHomeTab() {
        switchScene("/fxml/login.fxml", "Login");
    }

    @FXML
    private void handlePatientsTab() {
        switchScene("/fxml/patient.fxml", "Gestion Patients");
    }

    @FXML
    private void handleTraitementsTab() {
        switchScene("/fxml/treatment.fxml", "Gestion Traitements");
    }

    @FXML
    private void handleConsultationsTab() {
        switchScene("/fxml/consultation_list.fxml", "Historique Consultations");
    }

    @FXML
    private void handleAideTab() {
    }

    @FXML
    private void handleUserTab() {
    }

    private void switchScene(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            appointmentTable.getScene().setRoot(root);
            ((Stage) appointmentTable.getScene().getWindow()).setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
