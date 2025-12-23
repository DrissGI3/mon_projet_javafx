package models;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO {

    public static List<Treatment> getAllTreatments() {
        List<Treatment> treatments = new ArrayList<>();
        String query = "SELECT t.*, CONCAT(p.first_name, ' ', p.last_name) as patient_name " +
                "FROM treatments t " +
                "JOIN patients p ON t.patient_id = p.id " +
                "ORDER BY t.start_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Date startSql = rs.getDate("start_date");
                LocalDate start = (startSql != null) ? startSql.toLocalDate() : LocalDate.now();

                Date endSql = rs.getDate("end_date");
                LocalDate end = (endSql != null) ? endSql.toLocalDate() : null;

                treatments.add(new Treatment(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("patient_name"),
                        rs.getInt("doctor_id"),
                        "Doctor #" + rs.getInt("doctor_id"),
                        start,
                        end,
                        rs.getString("description"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treatments;
    }

    public static boolean addTreatment(int patientId, int doctorId, LocalDate start, LocalDate end, String desc,
            String status) {
        String query = "INSERT INTO treatments (patient_id, doctor_id, start_date, end_date, description, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, doctorId);
            pstmt.setDate(3, Date.valueOf(start));
            if (end != null)
                pstmt.setDate(4, Date.valueOf(end));
            else
                pstmt.setNull(4, Types.DATE);
            pstmt.setString(5, desc);
            pstmt.setString(6, status);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Treatment> getTreatmentsByPatientId(int patientId) {
        List<Treatment> treatments = new ArrayList<>();
        String query = "SELECT t.*, CONCAT(p.first_name, ' ', p.last_name) as patient_name " +
                "FROM treatments t " +
                "JOIN patients p ON t.patient_id = p.id " +
                "WHERE t.patient_id = ? " +
                "ORDER BY t.start_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Date startSql = rs.getDate("start_date");
                    LocalDate start = (startSql != null) ? startSql.toLocalDate() : LocalDate.now();

                    Date endSql = rs.getDate("end_date");
                    LocalDate end = (endSql != null) ? endSql.toLocalDate() : null;

                    treatments.add(new Treatment(
                            rs.getInt("id"),
                            rs.getInt("patient_id"),
                            rs.getString("patient_name"),
                            rs.getInt("doctor_id"),
                            "Doctor #" + rs.getInt("doctor_id"),
                            start,
                            end,
                            rs.getString("description"),
                            rs.getString("status")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treatments;
    }
}
