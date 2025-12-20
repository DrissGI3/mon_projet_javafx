package models;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class Appointment {
    private final IntegerProperty id;
    private final IntegerProperty patientId;
    private final StringProperty patientName; // For display
    private final IntegerProperty doctorId;
    private final StringProperty doctorName; // For display
    private final ObjectProperty<LocalDate> date;
    private final ObjectProperty<LocalTime> time;
    private final StringProperty reason;
    private final StringProperty status;
    private final ObjectProperty<LocalDateTime> createdAt;

    public Appointment(int id, int patientId, String patientName, int doctorId, String doctorName,
            LocalDate date, LocalTime time, String reason, String status, LocalDateTime createdAt) {
        this.id = new SimpleIntegerProperty(id);
        this.patientId = new SimpleIntegerProperty(patientId);
        this.patientName = new SimpleStringProperty(patientName);
        this.doctorId = new SimpleIntegerProperty(doctorId);
        this.doctorName = new SimpleStringProperty(doctorName);
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
        this.reason = new SimpleStringProperty(reason);
        this.status = new SimpleStringProperty(status);
        this.createdAt = new SimpleObjectProperty<>(createdAt);
    }

    // Getters and Properties
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

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public LocalTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public String getReason() {
        return reason.get();
    }

    public StringProperty reasonProperty() {
        return reason;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }
}
