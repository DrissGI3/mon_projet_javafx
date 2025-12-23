package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String SERVER_URL = "jdbc:mariadb://localhost:3306/";
    private static final String DB_NAME = "clinique";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void initialize() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            // 1. Create database if not exists
            try (Connection conn = DriverManager.getConnection(SERVER_URL, USER, PASSWORD);
                    Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
                System.out.println("✅ Database '" + DB_NAME + "' ensured.");
            }

            // 2. Create tables
            try (Connection conn = DriverManager.getConnection(SERVER_URL + DB_NAME, USER, PASSWORD);
                    Statement stmt = conn.createStatement()) {

                // Patients table (updated with medical info)
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS patients (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "first_name VARCHAR(50) NOT NULL," +
                        "last_name VARCHAR(50) NOT NULL," +
                        "date_of_birth DATE," +
                        "gender VARCHAR(10)," +
                        "phone VARCHAR(20)," +
                        "email VARCHAR(100)," +
                        "address TEXT," +
                        "social_security_number VARCHAR(20)," +
                        "allergies TEXT," +
                        "chronic_diseases TEXT," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ")");
                System.out.println("✅ Table 'patients' ensured.");

                // Appointments table
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS appointments (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "patient_id INT NOT NULL," +
                        "doctor_id INT NOT NULL," +
                        "appointment_date DATE NOT NULL," +
                        "appointment_time TIME NOT NULL," +
                        "reason TEXT," +
                        "status VARCHAR(20) DEFAULT 'Scheduled'," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE" +
                        ")");
                System.out.println("✅ Table 'appointments' ensured.");

                // Treatments table
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS treatments (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "patient_id INT NOT NULL," +
                        "doctor_id INT NOT NULL," +
                        "start_date DATE NOT NULL," +
                        "end_date DATE," +
                        "description TEXT," +
                        "status VARCHAR(20)," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE" +
                        ")");
                System.out.println("✅ Table 'treatments' ensured.");

                // Consultations table (updated with physical exam fields)
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS consultations (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "appointment_id INT NOT NULL UNIQUE," +
                        "clinical_signs TEXT," +
                        "physical_exam TEXT," +
                        "diagnosis TEXT," +
                        "prescription TEXT," +
                        "notes TEXT," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE" +
                        ")");
                System.out.println("✅ Table 'consultations' ensured.");

                // Prescribed Exams table
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS prescribed_exams (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "patient_id INT NOT NULL," +
                        "doctor_id INT," +
                        "exam_type VARCHAR(100)," +
                        "description TEXT," +
                        "status VARCHAR(20) DEFAULT 'Pending'," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE" +
                        ")");
                System.out.println("✅ Table 'prescribed_exams' ensured.");

                // Add sample data if empty
                java.sql.ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM patients");
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate(
                            "INSERT INTO patients (first_name, last_name, date_of_birth, gender, phone, email, address) VALUES "
                                    +
                                    "('Jean', 'Dupont', '1985-05-15', 'Masculin', '0612345678', 'jean.dupont@email.com', '123 Rue de Paris'),"
                                    +
                                    "('Marie', 'Curie', '1990-11-20', 'Féminin', '0687654321', 'marie.curie@email.com', '45 Avenue des Sciences')");
                    System.out.println("✅ Sample patients added.");
                }

            }
        } catch (Exception e) {
            System.err.println("❌ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
