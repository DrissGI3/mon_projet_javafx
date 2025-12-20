package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Session;
import models.Treatment;
import models.TreatmentDAO;

import java.time.LocalDate;

public class TraitementController {

    @FXML
    private TableView<Treatment> treatmentTable;
    @FXML
    private TableColumn<Treatment, Integer> colId;
    @FXML
    private TableColumn<Treatment, String> colPatient;
    @FXML
    private TableColumn<Treatment, String> colDoctor;
    @FXML
    private TableColumn<Treatment, LocalDate> colStart;
    @FXML
    private TableColumn<Treatment, LocalDate> colEnd;
    @FXML
    private TableColumn<Treatment, String> colStatus;
    @FXML
    private TableColumn<Treatment, String> colDesc;

    @FXML
    private Button btnAddTreatment;

    private ObservableList<Treatment> treatmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadTreatments();
        checkPermissions();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colPatient.setCellValueFactory(cellData -> cellData.getValue().patientNameProperty());
        colDoctor.setCellValueFactory(cellData -> cellData.getValue().doctorNameProperty());
        colStart.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        colEnd.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        colDesc.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    }

    private void loadTreatments() {
        treatmentList.setAll(TreatmentDAO.getAllTreatments());
        treatmentTable.setItems(treatmentList);
    }

    private void checkPermissions() {
        // Only Doctors can add treatments
        if (!Session.isDoctor()) {
            btnAddTreatment.setVisible(false);
            btnAddTreatment.setManaged(false);
        }
    }

    @FXML
    private void handleNewTreatment() {
        // Implementation for adding treatment (could open another dialog)
        System.out.println("Nouveau traitement clicked");
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

    private void switchScene(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            treatmentTable.getScene().setRoot(root);
            ((Stage) treatmentTable.getScene().getWindow()).setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
