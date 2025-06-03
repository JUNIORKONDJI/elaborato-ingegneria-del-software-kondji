package database;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String DEFAULT_DB_PATH = "db/booktrack.db";
    private static final String BACKUP_DB_PATH = "db/booktrack_backup.db";
    private static final String SCHEMA_FILE = "src/resources/schema.sql"; // Tu peux adapter ce chemin

    private static final String JDBC_PREFIX = "jdbc:sqlite:";

    public static String getDatabaseUrl() {
        try {
            Dotenv dotenv = Dotenv.load();
            String path = dotenv.get("BOOKTRACK_DB_PATH");
            if (path == null || path.isBlank()) return JDBC_PREFIX + DEFAULT_DB_PATH;
            return JDBC_PREFIX + path;
        } catch (Exception e) {
            return JDBC_PREFIX + DEFAULT_DB_PATH;
        }
    }

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(getDatabaseUrl())) {
            if (conn != null) {
                System.out.println("Connected to BookTrack DB");
                runSchemaFile(conn);
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
    }

    private static void runSchemaFile(Connection conn) {
        try {
            String schema = Files.readString(Paths.get(SCHEMA_FILE));
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(schema);
            stmt.close();
            System.out.println("Schema loaded successfully.");
        } catch (IOException | SQLException e) {
            System.err.println("Error running schema: " + e.getMessage());
        }
    }

    public static void backupDatabase() {
        try {
            Path dbPath = Paths.get(DEFAULT_DB_PATH);
            Path backupPath = Paths.get(BACKUP_DB_PATH);
            Files.copy(dbPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Backup successful.");
        } catch (IOException e) {
            System.err.println("Backup failed: " + e.getMessage());
        }
    }

    public static void restoreBackup() {
        try {
            Path dbPath = Paths.get(DEFAULT_DB_PATH);
            Path backupPath = Paths.get(BACKUP_DB_PATH);
            if (Files.exists(backupPath)) {
                Files.copy(backupPath, dbPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Restore successful.");
            } else {
                System.out.println("No backup file found.");
            }
        } catch (IOException e) {
            System.err.println("Restore failed: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getDatabaseUrl());
    }
}
