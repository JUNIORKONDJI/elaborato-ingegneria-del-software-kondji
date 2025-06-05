package test.java;

import coreentities.librarymanagement.DatabaseConnectionManager;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionManagerTest {

    @Test
    public void testGetConnection() {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            assertNotNull(connection, "La connexion ne doit pas être null");
            assertFalse(connection.isClosed(), "La connexion ne doit pas être fermée");
            System.out.println("Connexion établie avec succès !");
        } catch (SQLException e) {
            fail("Erreur lors de l'ouverture de la connexion à la base de données : " + e.getMessage());
        }
    }

    @Test
    public void testMultipleConnections() {
        try (Connection conn1 = DatabaseConnectionManager.getConnection();
             Connection conn2 = DatabaseConnectionManager.getConnection()) {
            assertNotNull(conn1);
            assertNotNull(conn2);
            assertNotSame(conn1, conn2, "Chaque appel doit retourner une nouvelle connexion");
        } catch (SQLException e) {
            fail("Impossible d'établir plusieurs connexions : " + e.getMessage());
        }
    }

    @Test
    public void testInvalidConnectionHandling() {
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            conn.close();
            assertTrue(conn.isClosed(), "Connexion fermée correctement");
        } catch (SQLException e) {
            fail("Exception inattendue lors de la fermeture de la connexion : " + e.getMessage());
        }
    }
}
