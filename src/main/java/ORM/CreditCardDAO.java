package main.java.ORM;

import java.sql.*;
import java.util.ArrayList;
import main.java.CoreEntities.CreditCard;

public class CreditCardDAO {

    private Connection connection;

    public CreditCardDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void addCreditCard(String cardNumber, String cardExpirationDate, String cardSecurityCode) throws SQLException {
        String sql = "INSERT INTO \"CreditCard\" (cardNumber, cardExpirationDate, cardSecurityCode) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.setString(2, cardExpirationDate);
            preparedStatement.setString(3, cardSecurityCode);
            preparedStatement.executeUpdate();
            System.out.println("Credit Card added successfully.");
        }
    }

    public void removeCreditCard(String cardNumber) throws SQLException {
        String sql = "DELETE FROM \"CreditCard\" WHERE cardNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.executeUpdate();
            System.out.println("Credit Card removed successfully.");
        }
    }

    public CreditCard getCreditCard(String cardNumber) throws SQLException, ClassNotFoundException {
        CreditCard creditCard = null;
        String sql = "SELECT * FROM \"CreditCard\" WHERE cardNumber = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cardNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String[] data = new UserDAO().getPersonalData("CreditCard", cardNumber);
                    creditCard = new CreditCard(data[0], data[1], rs.getString("cardNumber"), rs.getString("cardExpirationDate"), rs.getString("cardSecurityCode"));
                }
            }
        }
        return creditCard;
    }

    public CreditCard getCreditCard(int userId) throws SQLException, ClassNotFoundException {
        CreditCard creditCard = null;
        String sql = "SELECT * FROM \"CreditCard\" WHERE cardNumber = (SELECT CreditCard FROM \"User\" WHERE id = ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String[] data = new UserDAO().getPersonalData("CreditCard", rs.getString("cardNumber"));
                    creditCard = new CreditCard(data[0], data[1], rs.getString("cardNumber"), rs.getString("cardExpirationDate"), rs.getString("cardSecurityCode"));
                }
            }
        }
        return creditCard;
    }

    public ArrayList<CreditCard> getAllCreditCards() throws SQLException, ClassNotFoundException {
        ArrayList<CreditCard> creditCards = new ArrayList<>();
        String sql = "SELECT * FROM \"CreditCard\"";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String[] data = new UserDAO().getPersonalData("CreditCard", rs.getString("cardNumber"));
                creditCards.add(new CreditCard(data[0], data[1], rs.getString("cardNumber"), rs.getString("cardExpirationDate"), rs.getString("cardSecurityCode")));
            }
        }
        return creditCards;
    }
}
