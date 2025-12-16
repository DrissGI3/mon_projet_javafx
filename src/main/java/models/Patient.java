package models;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.Period;

public class Patient {
    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final ObjectProperty<LocalDate> dateOfBirth;
    private final StringProperty gender;
    private final StringProperty phone;
    private final StringProperty email;
    private final StringProperty address;
    private final StringProperty socialSecurityNumber;
    private final StringProperty createdAt;

    // Constructor with all parameters
    public Patient(int id, String firstName, String lastName, LocalDate dateOfBirth,
            String gender, String phone, String email, String address,
            String socialSecurityNumber, String createdAt) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.dateOfBirth = new SimpleObjectProperty<>(dateOfBirth);
        this.gender = new SimpleStringProperty(gender);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.socialSecurityNumber = new SimpleStringProperty(socialSecurityNumber);
        this.createdAt = new SimpleStringProperty(createdAt);
    }

    // Default constructor
    public Patient() {
        this(0, "", "", null, "", "", "", "", "", "");
    }

    // Getters for properties (for TableView binding)
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public ObjectProperty<LocalDate> dateOfBirthProperty() {
        return dateOfBirth;
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty addressProperty() {
        return address;
    }

    public StringProperty socialSecurityNumberProperty() {
        return socialSecurityNumber;
    }

    public StringProperty createdAtProperty() {
        return createdAt;
    }

    // Getters for values
    public int getId() {
        return id.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber.get();
    }

    public String getCreatedAt() {
        return createdAt.get();
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setSocialSecurityNumber(String ssn) {
        this.socialSecurityNumber.set(ssn);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt.set(createdAt);
    }

    // Computed property: Full Name
    public String getFullName() {
        return firstName.get() + " " + lastName.get();
    }

    // Computed property: Age
    public int getAge() {
        if (dateOfBirth.get() == null)
            return 0;
        return Period.between(dateOfBirth.get(), LocalDate.now()).getYears();
    }

    // For TableView - Full Name Property
    public StringProperty fullNameProperty() {
        return new SimpleStringProperty(getFullName());
    }

    // For TableView - Age Property
    public IntegerProperty ageProperty() {
        return new SimpleIntegerProperty(getAge());
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + getId() +
                ", name='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", age=" + getAge() +
                '}';
    }
}
