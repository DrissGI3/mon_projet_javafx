package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.DatabaseConnection;
import models.Patient;
import models.PatientDAO;

public class PatientController {

    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, Integer> colId;
    @FXML
    private TableColumn<Patient, String> colEmail;
    @FXML
    private TableColumn<Patient, String> colFullName;
    @FXML
    private TableColumn<Patient, Integer> colAge;
    @FXML
    private TableColumn<Patient, String> colGender;
    @FXML
    private TableColumn<Patient, String> colTreatments;
    @FXML
    private TableColumn<Patient, Void> colActions;

    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    private Button btnAddPatient;
    @FXML
    private Button btnDeleteAll;
    @FXML
    private Button btnSearch;

    private ObservableList<Patient> patientList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        System.out.println("🔄 Initializing Patient Interface...");

        // Test database connection
        if (!DatabaseConnection.testConnection()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de Connexion",
                    "Impossible de se connecter à la base de données.\n" +
                            "Vérifiez que MariaDB est démarré et que les identifiants sont corrects.");
            return;
        }

        // Setup table columns
        setupTableColumns();

        // Load patient data
        loadPatients();

        // Setup filter combobox
        filterComboBox.getItems().addAll("Nom", "Email", "Téléphone", "Tous");
        filterComboBox.setValue("Tous");
    }

    /**
     * Setup table columns with cell value factories
     */
    private void setupTableColumns() {
        // ID column
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        // Email column
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        // Full Name column
        colFullName.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());

        // Age column
        colAge.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());

        // Gender column
        colGender.setCellValueFactory(cellData -> cellData.getValue().genderProperty());

        // Treatments column (placeholder)
        colTreatments.setCellFactory(column -> new TableCell<Patient, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText("None"); // Placeholder - update when treatments table exists
                }
            }
        });

        // Actions column with buttons
        colActions.setCellFactory(column -> new TableCell<Patient, Void>() {
            private final Button btnView = new Button("Aperçu&Mise-à-jour");
            private final Button btnDelete = new Button("Supprimer");
            private final Button btnPdf = new Button("Extraire-PDF");
            private final HBox actionBox = new HBox(5, btnView, btnDelete, btnPdf);

            {
                btnView.getStyleClass().add("action-btn-view");
                btnDelete.getStyleClass().add("action-btn-delete");
                btnPdf.getStyleClass().add("action-btn-pdf");
                actionBox.setAlignment(Pos.CENTER);

                btnView.setOnAction(event -> {
                    Patient patient = getTableView().getItems().get(getIndex());
                    handleViewPatient(patient);
                });

                btnDelete.setOnAction(event -> {
                    Patient patient = getTableView().getItems().get(getIndex());
                    handleDeletePatient(patient);
                });

                btnPdf.setOnAction(event -> {
                    Patient patient = getTableView().getItems().get(getIndex());
                    handleExtractPdf(patient);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionBox);
                }
            }
        });
    }

    /**
     * Load all patients from database
     */
    private void loadPatients() {
        patientList.clear();
        patientList.addAll(PatientDAO.getAllPatients());
        patientTable.setItems(patientList);
        System.out.println("✅ Loaded " + patientList.size() + " patients into table");
    }

    /**
     * Search patients
     */
    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().trim();

        if (searchTerm.isEmpty()) {
            loadPatients(); // Reload all if search is empty
            return;
        }

        patientList.clear();
        patientList.addAll(PatientDAO.searchPatients(searchTerm));
        patientTable.setItems(patientList);

        System.out.println("🔍 Search results: " + patientList.size() + " patients found");
    }

    /**
     * View/Update patient details
     */
    private void handleViewPatient(Patient patient) {
        System.out.println("👁️ View patient: " + patient);
        showAlert(Alert.AlertType.INFORMATION, "Aperçu Patient",
                "Patient: " + patient.getFullName() + "\n" +
                        "Email: " + patient.getEmail() + "\n" +
                        "Téléphone: " + patient.getPhone() + "\n" +
                        "Âge: " + patient.getAge() + " ans\n" +
                        "Adresse: " + patient.getAddress());
        // TODO: Open edit dialog
    }

    /**
     * Delete patient
     */
    private void handleDeletePatient(Patient patient) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Supprimer le patient");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer " + patient.getFullName() + " ?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (PatientDAO.deletePatient(patient.getId())) {
                    patientList.remove(patient);
                    showAlert(Alert.AlertType.INFORMATION, "Succès",
                            "Patient supprimé avec succès");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur",
                            "Impossible de supprimer le patient");
                }
            }
        });
    }

    /**
     * Extract PDF for patient
     */
    private void handleExtractPdf(Patient patient) {
        System.out.println("📄 Extract PDF for: " + patient.getFullName());
        showAlert(Alert.AlertType.INFORMATION, "Extraire PDF",
                "Fonctionnalité d'extraction PDF pour " + patient.getFullName() +
                        "\n(À implémenter)");
        // TODO: Implement PDF generation
    }

    /**
     * Add new patient
     */
    @FXML
    private void handleAddPatient() {
        System.out.println("➕ Add New Patient Clicked");
        showAlert(Alert.AlertType.INFORMATION, "Ajouter Patient",
                "Formulaire d'ajout de patient\n(À implémenter)");
        // TODO: Open add patient dialog
    }

    /**
     * Delete all patients
     */
    @FXML
    private void handleDeleteAll() {
        Alert confirmation = new Alert(Alert.AlertType.WARNING);
        confirmation.setTitle("⚠️ Attention");
        confirmation.setHeaderText("Supprimer TOUS les patients");
        confirmation.setContentText("Cette action est IRRÉVERSIBLE!\n" +
                "Êtes-vous absolument sûr de vouloir supprimer tous les patients ?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (PatientDAO.deleteAllPatients()) {
                    patientList.clear();
                    showAlert(Alert.AlertType.INFORMATION, "Succès",
                            "Tous les patients ont été supprimés");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur",
                            "Impossible de supprimer les patients");
                }
            }
        });
    }

    // Tab Navigation Handlers
    @FXML
    private void handleHomeTab() {
        System.out.println("🏠 Navigate to Home");
        try {
            Stage stage = (Stage) patientTable.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/secretary.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/css/admin.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRendezVousTab() {
        System.out.println("📅 Rendez-vous tab (not implemented)");
    }

    @FXML
    private void handleTraitementsTab() {
        System.out.println("💊 Traitements tab (not implemented)");
    }

    @FXML
    private void handleAideTab() {
        System.out.println("❓ Aide tab (not implemented)");
    }

    @FXML
    private void handleUserTab() {
        System.out.println("👤 User tab (not implemented)");
    }

    /**
     * Show alert dialog
     */
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
