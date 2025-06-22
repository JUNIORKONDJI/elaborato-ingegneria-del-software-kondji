package main.java.LibraryManagement;

import main.java.ORM.AdminDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Classe controller per la gestione delle operazioni di amministrazione extra come reset e inizializzazione del database.
 */
public class AdminExtraController {

    /**
     * Aggiorna la password dell'account admin nel database.
     * @param newPassword la nuova password da impostare
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateAdminPassword(String newPassword) throws SQLException, ClassNotFoundException {
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.updatePassword(newPassword);
    }

    /**
     * Esegue il reset completo del database eliminando e ricreando le tabelle secondo lo script reset.sql.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void resetDatabaseStructure() throws SQLException, ClassNotFoundException {
        String sql = loadSqlScript("src/main/sql/reset.sql");

        if (sql != null) {
            AdminDAO adminDAO = new AdminDAO();
            adminDAO.resetDatabase(sql);
        }
    }

    /**
     * Popola il database con dati predefiniti a scopo di test, dopo averlo resettato.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void initializeDefaultData() throws SQLException, ClassNotFoundException {
        resetDatabaseStructure();

        String sql = loadSqlScript("src/main/sql/default.sql");

        if (sql != null) {
            AdminDAO adminDAO = new AdminDAO();
            adminDAO.generateDefaultDatabase(sql);
        }
    }

    /**
     * Carica uno script SQL da un file.
     * @param filePath percorso del file SQL
     * @return il contenuto del file come stringa, oppure null in caso di errore
     */
    private String loadSqlScript(String filePath) {
        StringBuilder sqlBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento del file SQL: " + e.getMessage());
            return null;
        }

        return sqlBuilder.toString();
    }
}
