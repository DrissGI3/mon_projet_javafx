package models;

public class Session {
    private static String currentRole;
    private static String currentUsername;

    public static void setSession(String username, String role) {
        currentUsername = username;
        currentRole = role;
    }

    public static String getRole() {
        return currentRole;
    }

    public static String getUsername() {
        return currentUsername;
    }

    public static void clear() {
        currentUsername = null;
        currentRole = null;
    }

    public static boolean isDoctor() {
        return "Medecin".equalsIgnoreCase(currentRole);
    }

    public static boolean isSecretary() {
        return "secretaire".equalsIgnoreCase(currentRole);
    }
}
