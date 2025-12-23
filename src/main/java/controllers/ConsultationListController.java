package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import models.Consultation;
import models.ConsultationDAO;

import java.time.LocalDateTime;

public class ConsultationListController {

    @FXML
    private TableView<Consultation> consultationTable;
    @FXML
    private TableColumn<Consultation, Integer> colId;
    @FXML
    private TableColumn<Consultation, String> colPatient;
    @FXML
    private TableColumn<Consultation, LocalDateTime> colDate;
    @FXML
    private TableColumn<Consultation, String> colDiagnosis;
    @FXML
    private TableColumn<Consultation, String> colPrescription;
    @FXML
    private TableColumn<Consultation, String> colNotes;

    private ObservableList<Consultation> consultationList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadConsultations();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colPatient.setCellValueFactory(cellData -> cellData.getValue().patientNameProperty());
        colDate.setCellValueFactory(cellData -> cellData.getValue().createdAtProperty());
        colDiagnosis.setCellValueFactory(cellData -> cellData.getValue().diagnosisProperty());
        colPrescription.setCellValueFactory(cellData -> cellData.getValue().prescriptionProperty());
        colNotes.setCellValueFactory(cellData -> cellData.getValue().notesProperty());
    }

    private void loadConsultations() {
        consultationList.setAll(ConsultationDAO.getAllConsultations());
        consultationTable.setItems(consultationList);
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
    private void handleRendezVousTab() {
        switchScene("/fxml/appointment.fxml", "Gestion Rendez-vous");
    }

    @FXML
    private void handleTraitementsTab() {
        switchScene("/fxml/treatment.fxml", "Gestion Traitements");
    }

    @FXML
    private void handleLogout() {
        switchScene("/fxml/login.fxml", "Login");
    }

    private void switchScene(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            consultationTable.getScene().setRoot(root);
            ((Stage) consultationTable.getScene().getWindow()).setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
