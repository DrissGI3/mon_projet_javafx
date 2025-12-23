package models;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public static List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        // Note: Joining with patients to get patient name.
        // We don't have a doctor name yet, so we just use ID as a placeholder for name.
        String query = "SELECT a.*, CONCAT(p.first_name, ' ', p.last_name) as patient_name " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.id " +
                "ORDER BY a.appointment_date, a.appointment_time";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                LocalDateTime createdAt = null;
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    createdAt = ts.toLocalDateTime();
                }

                Date dateSql = rs.getDate("appointment_date");
                LocalDate date = (dateSql != null) ? dateSql.toLocalDate() : LocalDate.now();

                Time timeSql = rs.getTime("appointment_time");
                LocalTime time = (timeSql != null) ? timeSql.toLocalTime() : LocalTime.now();

                appointments.add(new Appointment(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("patient_name"),
                        rs.getInt("doctor_id"),
                        "Doctor #" + rs.getInt("doctor_id"), // Placeholder for doctor name
                        date,
                        time,
                        rs.getString("reason"),
                        rs.getString("status"),
                        createdAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public static boolean addAppointment(int patientId, int doctorId, LocalDate date, LocalTime time, String reason,
            String status) {
        String query = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, reason, status) "
                +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, doctorId);
            pstmt.setDate(3, Date.valueOf(date));
            pstmt.setTime(4, Time.valueOf(time));
            pstmt.setString(5, reason);
            pstmt.setString(6, status);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateStatus(int id, String status) {
        String query = "UPDATE appointments SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
