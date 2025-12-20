package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.AppointmentDAO;
import models.Patient;
import models.PatientDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AddAppointmentController {

    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private TextField doctorIdField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeField;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private TextArea reasonArea;

    private boolean saveClicked = false;
    private AppointmentController parentController;

    @FXML
    public void initialize() {
        // Load patients
        List<Patient> patients = PatientDAO.getAllPatients();
        patientComboBox.setItems(FXCollections.observableArrayList(patients));
        patientComboBox.setConverter(new StringConverter<Patient>() {
            @Override
            public String toString(Patient patient) {
                return patient == null ? "" : patient.getFullName() + " (ID: " + patient.getId() + ")";
            }

            @Override
            public Patient fromString(String string) {
                return null;
            }
        });

        // Load statuses
        statusComboBox.setItems(FXCollections.observableArrayList("SCHEDULED", "CONFIRMED", "COMPLETED", "CANCELLED"));
        statusComboBox.setValue("SCHEDULED");

        datePicker.setValue(LocalDate.now());
    }

    public void setParentController(AppointmentController parentController) {
        this.parentController = parentController;
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            try {
                int patientId = patientComboBox.getValue().getId();
                int doctorId = Integer.parseInt(doctorIdField.getText());
                LocalDate date = datePicker.getValue();
                LocalTime time = LocalTime.parse(timeField.getText());
                String reason = reasonArea.getText();
                String status = statusComboBox.getValue();

                boolean success = AppointmentDAO.addAppointment(patientId, doctorId, date, time, reason, status);

                if (success) {
                    saveClicked = true;
                    closeStage();
                } else {
                    showAlert("Erreur", "Base de données : Échec de l'enregistrement.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Erreur", "Une erreur est survenue lors de l'enregistrement.");
            }
        }
    }

    @FXML
    private void handleCancel() {
        closeStage();
    }

    private void closeStage() {
        ((Stage) patientComboBox.getScene().getWindow()).close();
    }

    private boolean isInputValid() {
        String error = "";

        if (patientComboBox.getValue() == null)
            error += "- Patient non sélectionné\n";

        if (doctorIdField.getText() == null || doctorIdField.getText().isEmpty()) {
            error += "- ID Docteur vide\n";
        } else {
            try {
                Integer.parseInt(doctorIdField.getText());
            } catch (NumberFormatException e) {
                error += "- ID Docteur doit être un nombre\n";
            }
        }

        if (datePicker.getValue() == null)
            error += "- Date non sélectionnée\n";

        if (timeField.getText() == null || timeField.getText().isEmpty()) {
            error += "- Heure vide\n";
        } else {
            try {
                LocalTime.parse(timeField.getText());
            } catch (DateTimeParseException e) {
                error += "- Format heure invalide (HH:mm)\n";
            }
        }

        if (reasonArea.getText() == null || reasonArea.getText().trim().isEmpty())
            error += "- Motif vide\n";

        if (error.isEmpty())
            return true;

        showAlert("Champs Invalides", error);
        return false;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
