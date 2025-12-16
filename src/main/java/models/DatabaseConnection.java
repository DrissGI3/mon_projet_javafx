package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Database configuration
    private static final String URL = "jdbc:mariadb://localhost:3306/clinique";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Default empty password, update as needed

    // Singleton connection
    private static Connection connection = null;

    /**
     * Get database connection
     * 
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load MariaDB driver
                Class.forName("org.mariadb.jdbc.Driver");

                // Establish connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Database connection established successfully");
            }
            return connection;

        } catch (ClassNotFoundException e) {
            System.err.println("❌ MariaDB Driver not found: " + e.getMessage());
            e.printStackTrace();
            return null;

        } catch (SQLException e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test database connection
     * 
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        Connection conn = getConnection();
        return conn != null;
    }
}
