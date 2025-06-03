package main.java.ORM;

import main.java.DomainModel.PayPal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayPalDAO {

    private Connection connection;

    public PayPalDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void addPayPalAccount(String accountEmail, String accountPassword) throws SQLException {

        String sql = String.format("INSERT INTO \"PayPal\" (accountEmail, accountPassword) VALUES ('%s', '%s')",
                accountEmail, accountPassword);

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("PayPal account added successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public void removePayPalAccount(int uniqueCode) throws SQLException {

        String sql = String.format("DELETE FROM \"PayPal\" WHERE code = %d", uniqueCode);

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("PayPal account removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public PayPal getPayPalAccount(int uniqueCode) throws SQLException {

        PayPal payPal = null;

        String sql = String.format("SELECT * FROM \"PayPal\" WHERE code = %d", uniqueCode);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String accountEmail = resultSet.getString("accountEmail");
                String accountPassword = resultSet.getString("accountPassword");
                payPal = new PayPal(uniqueCode, accountEmail, accountPassword);
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (resultSet != null) resultSet.close();
        }

        return payPal;
    }

    public int getUniqueCode(String accountEmail) throws SQLException {

        int uniqueCode = 0;

        String sql = String.format("SELECT code FROM \"PayPal\" WHERE accountEmail = '%s'", accountEmail);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                uniqueCode = resultSet.getInt("code");
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (resultSet != null) resultSet.close();
        }

        return uniqueCode;
    }
}
