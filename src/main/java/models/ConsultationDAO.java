package models;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultationDAO {

    public static boolean addConsultation(int appointmentId, String clinicalSigns, String physicalExam,
            String diagnosis, String prescription, String notes) {
        String query = "INSERT INTO consultations (appointment_id, clinical_signs, physical_exam, diagnosis, prescription, notes) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, appointmentId);
            pstmt.setString(2, clinicalSigns);
            pstmt.setString(3, physicalExam);
            pstmt.setString(4, diagnosis);
            pstmt.setString(5, prescription);
            pstmt.setString(6, notes);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Consultation getConsultationByAppointmentId(int appointmentId) {
        String query = "SELECT * FROM consultations WHERE appointment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, appointmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Timestamp ts = rs.getTimestamp("created_at");
                    LocalDateTime createdAt = (ts != null) ? ts.toLocalDateTime() : LocalDateTime.now();
                    return new Consultation(
                            rs.getInt("id"),
                            rs.getInt("appointment_id"),
                            null, // Patient name not easily available here without join
                            rs.getString("clinical_signs"),
                            rs.getString("physical_exam"),
                            rs.getString("diagnosis"),
                            rs.getString("prescription"),
                            rs.getString("notes"),
                            createdAt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Consultation> getConsultationsByPatientId(int patientId) {
        List<Consultation> consultations = new ArrayList<>();
        String query = "SELECT c.* FROM consultations c " +
                "JOIN appointments a ON c.appointment_id = a.id " +
                "WHERE a.patient_id = ? ORDER BY c.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Timestamp ts = rs.getTimestamp("created_at");
                    LocalDateTime createdAt = (ts != null) ? ts.toLocalDateTime() : LocalDateTime.now();
                    consultations.add(new Consultation(
                            rs.getInt("id"),
                            rs.getInt("appointment_id"),
                            null, // Patient name not easily available here
                            rs.getString("clinical_signs"),
                            rs.getString("physical_exam"),
                            rs.getString("diagnosis"),
                            rs.getString("prescription"),
                            rs.getString("notes"),
                            createdAt));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultations;
    }

    public static List<Consultation> getAllConsultations() {
        List<Consultation> consultations = new ArrayList<>();
        String query = "SELECT c.*, CONCAT(p.first_name, ' ', p.last_name) as patient_name " +
                "FROM consultations c " +
                "JOIN appointments a ON c.appointment_id = a.id " +
                "JOIN patients p ON a.patient_id = p.id " +
                "ORDER BY c.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("created_at");
                LocalDateTime createdAt = (ts != null) ? ts.toLocalDateTime() : LocalDateTime.now();
                consultations.add(new Consultation(
                        rs.getInt("id"),
                        rs.getInt("appointment_id"),
                        rs.getString("patient_name"),
                        rs.getString("clinical_signs"),
                        rs.getString("physical_exam"),
                        rs.getString("diagnosis"),
                        rs.getString("prescription"),
                        rs.getString("notes"),
                        createdAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultations;
    }
}
