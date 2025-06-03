
package main.java.ORM;

import main.java.CoreEntities.CreditCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreditCardDAO {

    private Connection connection;

    public CreditCardDAO() {

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public void addCreditCard(String cardNumber, String cardExpirationDate, String cardSecurityCode) throws SQLException, ClassNotFoundException {

        String sql = String.format("INSERT INTO \"CreditCard\" (cardNumber, cardExpirationDate, cardSecurityCode) " +
                "VALUES ('%s', '%s', '%s')", cardNumber, cardExpirationDate, cardSecurityCode);

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Credit Card added successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

    }

    public void removeCreditCard(String cardNumber) throws SQLException, ClassNotFoundException {

        String sql = String.format("DELETE FROM \"CreditCard\" WHERE cardNumber = '%s'", cardNumber);

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Credit Card removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

    }

    public CreditCard getCreditCard(String cardNumber) throws SQLException, ClassNotFoundException {

        CreditCard creditCard = null;

        String sql = String.format("SELECT * FROM \"CreditCard\" WHERE cardNumber = '%s'", cardNumber);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String[] data = new UserDAO().getPersonalData("CreditCard", cardNumber);
                creditCard = new CreditCard(data[0], data[1], resultSet.getString("cardNumber"), resultSet.getString("cardExpirationDate"), resultSet.getString("cardSecurityCode"));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return creditCard;

    }

    public CreditCard getCreditCard(int userId) throws SQLException, ClassNotFoundException {

        CreditCard creditCard = null;

        String sql = String.format("SELECT * FROM \"CreditCard\" WHERE cardNumber = (SELECT CreditCard FROM \"User\" WHERE id = %d)", userId);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String[] data = new UserDAO().getPersonalData("CreditCard", resultSet.getString("cardNumber"));
                creditCard = new CreditCard(data[0], data[1], resultSet.getString("cardNumber"), resultSet.getString("cardExpirationDate"), resultSet.getString("cardSecurityCode"));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return creditCard;

    }

    public ArrayList<CreditCard> getAllCreditCards() throws SQLException, ClassNotFoundException {

        ArrayList<CreditCard> creditCards = new ArrayList<CreditCard>();

        String sql = String.format("SELECT * FROM \"CreditCard\"");

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String[] data = new UserDAO().getPersonalData("CreditCard", resultSet.getString("cardNumber"));
                creditCards.add(new CreditCard(data[0], data[1], resultSet.getString("cardNumber"), resultSet.getString("cardExpirationDate"), resultSet.getString("cardSecurityCode")));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return creditCards;

    }

}
