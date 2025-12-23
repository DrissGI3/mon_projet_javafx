package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Consultation;
import models.ConsultationDAO;
import models.Patient;
import models.PatientDAO;
import models.Treatment;
import models.TreatmentDAO;

import java.util.List;

public class Patient360Controller {

    @FXML
    private Label patientNameLabel;
    @FXML
    private Label ageGenderLabel;
    @FXML
    private Label allergiesLabel;
    @FXML
    private Label chronicDiseasesLabel;

    @FXML
    private TableView<Consultation> historyTable;
    @FXML
    private TableColumn<Consultation, String> colDate;
    @FXML
    private TableColumn<Consultation, String> colDiagnosis;
    @FXML
    private TableColumn<Consultation, String> colPrescription;

    @FXML
    private ListView<String> treatmentsList;
    @FXML
    private ListView<String> examsList;

    private Patient patient;

    public void setPatient(Patient patient) {
        this.patient = patient;
        updateUI();
        loadHistory();
    }

    private void updateUI() {
        patientNameLabel.setText(patient.getFullName());
        ageGenderLabel.setText(patient.getAge() + " ans / " + patient.getGender());
        allergiesLabel
                .setText(patient.getAllergies() != null && !patient.getAllergies().isEmpty() ? patient.getAllergies()
                        : "Aucune");
        chronicDiseasesLabel.setText(patient.getChronicDiseases() != null && !patient.getChronicDiseases().isEmpty()
                ? patient.getChronicDiseases()
                : "Aucune");
    }

    private void loadHistory() {
        // Consultations
        List<Consultation> consultations = ConsultationDAO.getConsultationsByPatientId(patient.getId());
        ObservableList<Consultation> obsCons = FXCollections.observableArrayList(consultations);
        historyTable.setItems(obsCons);

        colDate.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCreatedAt().toString()));
        colDiagnosis.setCellValueFactory(data -> data.getValue().diagnosisProperty());
        colPrescription.setCellValueFactory(data -> data.getValue().prescriptionProperty());

        // Treatments
        List<Treatment> treatments = TreatmentDAO.getTreatmentsByPatientId(patient.getId());
        treatmentsList.getItems().clear();
        for (Treatment t : treatments) {
            treatmentsList.getItems().add(t.getStartDate() + " - " + t.getStatus() + ": " + t.getDescription());
        }

        // Exams (Empty for now as we just created the table)
        examsList.getItems().clear();
        examsList.getItems().add("Chargement des examens...");
    }

    @FXML
    private void handleBack() {
        switchScene("/fxml/patient.fxml", "Gestion Patients");
    }

    @FXML
    private void handleEditMedicalInfo() {
        // Simplified edit for allergies/chronic diseases
        TextInputDialog dialog = new TextInputDialog(patient.getAllergies());
        dialog.setTitle("Modifier Infos Médicales");
        dialog.setHeaderText("Modifier les allergies de " + patient.getFullName());
        dialog.setContentText("Allergies:");

        dialog.showAndWait().ifPresent(result -> {
            patient.setAllergies(result);
            PatientDAO.updatePatient(patient);
            updateUI();
        });
    }

    @FXML
    private void handlePrescribeExam() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Prescription d'examen");
        alert.setHeaderText(null);
        alert.setContentText("Fonctionnalité de prescription d'examen à implémenter.");
        alert.show();
    }

    private void switchScene(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            patientNameLabel.getScene().setRoot(root);
            ((Stage) patientNameLabel.getScene().getWindow()).setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
