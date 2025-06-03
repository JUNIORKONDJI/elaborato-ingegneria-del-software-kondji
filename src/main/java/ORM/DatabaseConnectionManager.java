package main.java.ORM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton per gestire la connessione al database PostgreSQL
 */
public class DatabaseConnectionManager {

    private static DatabaseConnectionManager instance;
    private static final String URL = "jdbc:postgresql://localhost:5432/booktrack_db"; // ← Cambia con il tuo database
    private static final String USER = "postgres"; // ← Cambia con il tuo utente DB
    private static final String PASSWORD = "password"; // ← Cambia con la tua password DB

    private Connection connection;

    private DatabaseConnectionManager() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Ottieni l’istanza singleton del gestore di connessione
     */
    public static DatabaseConnectionManager getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }

    /**
     * Restituisce la connessione al database
     */
    public Connection getConnection() {
        return this.connection;
    }
}
