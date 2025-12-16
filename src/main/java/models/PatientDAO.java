package models;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    /**
     * Get all patients from database
     */
    public static List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Patient patient = extractPatientFromResultSet(rs);
                patients.add(patient);
            }

            System.out.println("✅ Loaded " + patients.size() + " patients from database");

        } catch (SQLException e) {
            System.err.println("❌ Error fetching patients: " + e.getMessage());
            e.printStackTrace();
        }

        return patients;
    }

    /**
     * Get patient by ID
     */
    public static Patient getPatientById(int id) {
        String query = "SELECT * FROM patients WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractPatientFromResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching patient #" + id + ": " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Add new patient
     */
    public static boolean addPatient(Patient patient) {
        String query = "INSERT INTO patients (first_name, last_name, date_of_birth, gender, " +
                "phone, email, address, social_security_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, patient.getFirstName());
            pstmt.setString(2, patient.getLastName());
            pstmt.setDate(3, Date.valueOf(patient.getDateOfBirth()));
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getPhone());
            pstmt.setString(6, patient.getEmail());
            pstmt.setString(7, patient.getAddress());
            pstmt.setString(8, patient.getSocialSecurityNumber());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Patient added successfully: " + patient.getFullName());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Error adding patient: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Update existing patient
     */
    public static boolean updatePatient(Patient patient) {
        String query = "UPDATE patients SET first_name = ?, last_name = ?, date_of_birth = ?, " +
                "gender = ?, phone = ?, email = ?, address = ?, social_security_number = ? " +
                "WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, patient.getFirstName());
            pstmt.setString(2, patient.getLastName());
            pstmt.setDate(3, Date.valueOf(patient.getDateOfBirth()));
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getPhone());
            pstmt.setString(6, patient.getEmail());
            pstmt.setString(7, patient.getAddress());
            pstmt.setString(8, patient.getSocialSecurityNumber());
            pstmt.setInt(9, patient.getId());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Patient updated successfully: " + patient.getFullName());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Error updating patient: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Delete patient by ID
     */
    public static boolean deletePatient(int id) {
        String query = "DELETE FROM patients WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Patient #" + id + " deleted successfully");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Error deleting patient: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Delete all patients (use with caution!)
     */
    public static boolean deleteAllPatients() {
        String query = "DELETE FROM patients";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(query);
            System.out.println("⚠️ Deleted " + rowsAffected + " patients from database");
            return true;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting all patients: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Search patients by name, email, or phone
     */
    public static List<Patient> searchPatients(String searchTerm) {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients WHERE " +
                "LOWER(first_name) LIKE ? OR " +
                "LOWER(last_name) LIKE ? OR " +
                "LOWER(email) LIKE ? OR " +
                "phone LIKE ? " +
                "ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + searchTerm.toLowerCase() + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Patient patient = extractPatientFromResultSet(rs);
                patients.add(patient);
            }

            System.out.println("🔍 Found " + patients.size() + " patients matching: " + searchTerm);

        } catch (SQLException e) {
            System.err.println("❌ Error searching patients: " + e.getMessage());
            e.printStackTrace();
        }

        return patients;
    }

    /**
     * Helper method to extract Patient from ResultSet
     */
    private static Patient extractPatientFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        Date dob = rs.getDate("date_of_birth");
        LocalDate dateOfBirth = dob != null ? dob.toLocalDate() : null;
        String gender = rs.getString("gender");
        String phone = rs.getString("phone");
        String email = rs.getString("email");
        String address = rs.getString("address");
        String ssn = rs.getString("social_security_number");
        String createdAt = rs.getTimestamp("created_at").toString();

        return new Patient(id, firstName, lastName, dateOfBirth, gender,
                phone, email, address, ssn, createdAt);
    }
}
