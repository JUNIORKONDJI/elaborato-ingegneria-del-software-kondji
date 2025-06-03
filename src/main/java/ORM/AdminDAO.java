package main.java.ORM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDAO {

    private Connection connection;

    public AdminDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void updatePassword(String newPassword) throws SQLException {
        String sql = String.format("UPDATE \"User\" SET password = '%s' WHERE username = 'ADMIN'", newPassword);
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Admin password updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating admin password: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public void resetDatabase(String sql) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("\nBookTrack database reset successfully.");
        } catch (SQLException e) {
            System.err.println("Error resetting database: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public void generateDefaultDatabase(String sql) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("BookTrack default database generated successfully.");
        } catch (SQLException e) {
            System.err.println("Error generating default database: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
}
