package models;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Treatment {
    private final IntegerProperty id;
    private final IntegerProperty patientId;
    private final StringProperty patientName;
    private final IntegerProperty doctorId;
    private final StringProperty doctorName;
    private final ObjectProperty<LocalDate> startDate;
    private final ObjectProperty<LocalDate> endDate;
    private final StringProperty description;
    private final StringProperty status;

    public Treatment(int id, int patientId, String patientName, int doctorId, String doctorName,
            LocalDate startDate, LocalDate endDate, String description, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.patientId = new SimpleIntegerProperty(patientId);
        this.patientName = new SimpleStringProperty(patientName);
        this.doctorId = new SimpleIntegerProperty(doctorId);
        this.doctorName = new SimpleStringProperty(doctorName);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.description = new SimpleStringProperty(description);
        this.status = new SimpleStringProperty(status);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getPatientId() {
        return patientId.get();
    }

    public IntegerProperty patientIdProperty() {
        return patientId;
    }

    public String getPatientName() {
        return patientName.get();
    }

    public StringProperty patientNameProperty() {
        return patientName;
    }

    public int getDoctorId() {
        return doctorId.get();
    }

    public IntegerProperty doctorIdProperty() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName.get();
    }

    public StringProperty doctorNameProperty() {
        return doctorName;
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }
}
