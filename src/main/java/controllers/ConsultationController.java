package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import models.Appointment;
import models.AppointmentDAO;
import models.Consultation;
import models.ConsultationDAO;

public class ConsultationController {

    @FXML
    private Label patientLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private TextArea clinicalSignsArea;
    @FXML
    private TextArea physicalExamArea;
    @FXML
    private TextArea diagnosisArea;
    @FXML
    private TextArea prescriptionArea;
    @FXML
    private TextArea notesArea;

    private Appointment appointment;

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;

        String patientName = (appointment.getPatientName() != null) ? appointment.getPatientName() : "Inconnu";
        patientLabel.setText(patientName);

        String dateStr = (appointment.getDate() != null) ? appointment.getDate().toString() : "Date inconnue";
        String timeStr = (appointment.getTime() != null) ? appointment.getTime().toString() : "Heure inconnue";
        dateLabel.setText(dateStr + " à " + timeStr);

        // Load existing consultation if it exists
        Consultation existing = ConsultationDAO.getConsultationByAppointmentId(appointment.getId());
        if (existing != null) {
            clinicalSignsArea.setText(existing.getClinicalSigns() != null ? existing.getClinicalSigns() : "");
            physicalExamArea.setText(existing.getPhysicalExam() != null ? existing.getPhysicalExam() : "");
            diagnosisArea.setText(existing.getDiagnosis() != null ? existing.getDiagnosis() : "");
            prescriptionArea.setText(existing.getPrescription() != null ? existing.getPrescription() : "");
            notesArea.setText(existing.getNotes() != null ? existing.getNotes() : "");
        }
    }

    @FXML
    private void handleSave() {
        if (diagnosisArea.getText().trim().isEmpty()) {
            showAlert("Attention", "Le diagnostic ne peut pas être vide.");
            return;
        }

        boolean success = ConsultationDAO.addConsultation(
                appointment.getId(),
                clinicalSignsArea.getText(),
                physicalExamArea.getText(),
                diagnosisArea.getText(),
                prescriptionArea.getText(),
                notesArea.getText());

        if (success) {
            // Update appointment status to 'Completed'
            AppointmentDAO.updateStatus(appointment.getId(), "Complété");

            showAlert("Succès", "La consultation a été enregistrée avec succès.");
            closeWindow();
        } else {
            showAlert("Erreur", "Impossible d'enregistrer la consultation. Elle existe peut-être déjà.");
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) patientLabel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
