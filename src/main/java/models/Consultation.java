package models;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Consultation {
    private final IntegerProperty id;
    private final IntegerProperty appointmentId;
    private final StringProperty clinicalSigns;
    private final StringProperty physicalExam;
    private final StringProperty diagnosis;
    private final StringProperty prescription;
    private final StringProperty notes;
    private final StringProperty patientName;
    private final ObjectProperty<LocalDateTime> createdAt;

    public Consultation(int id, int appointmentId, String clinicalSigns, String physicalExam, String diagnosis,
            String prescription, String notes,
            LocalDateTime createdAt) {
        this(id, appointmentId, null, clinicalSigns, physicalExam, diagnosis, prescription, notes, createdAt);
    }

    public Consultation(int id, int appointmentId, String patientName, String clinicalSigns, String physicalExam,
            String diagnosis, String prescription, String notes,
            LocalDateTime createdAt) {
        this.id = new SimpleIntegerProperty(id);
        this.appointmentId = new SimpleIntegerProperty(appointmentId);
        this.patientName = new SimpleStringProperty(patientName);
        this.clinicalSigns = new SimpleStringProperty(clinicalSigns);
        this.physicalExam = new SimpleStringProperty(physicalExam);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.prescription = new SimpleStringProperty(prescription);
        this.notes = new SimpleStringProperty(notes);
        this.createdAt = new SimpleObjectProperty<>(createdAt);
    }

    // Getters and Property methods
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getAppointmentId() {
        return appointmentId.get();
    }

    public IntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    public String getClinicalSigns() {
        return clinicalSigns.get();
    }

    public StringProperty clinicalSignsProperty() {
        return clinicalSigns;
    }

    public String getPhysicalExam() {
        return physicalExam.get();
    }

    public StringProperty physicalExamProperty() {
        return physicalExam;
    }

    public String getDiagnosis() {
        return diagnosis.get();
    }

    public StringProperty diagnosisProperty() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription.get();
    }

    public StringProperty prescriptionProperty() {
        return prescription;
    }

    public String getNotes() {
        return notes.get();
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public String getPatientName() {
        return patientName.get();
    }

    public StringProperty patientNameProperty() {
        return patientName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }
}
