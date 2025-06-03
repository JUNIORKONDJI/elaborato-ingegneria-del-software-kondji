package main.java.ORM;

import java.sql.*;
import java.util.ArrayList;

public class BorrowingDAO {

    private Connection connection;

    public BorrowingDAO() {
        try {
            this.connection = DatabaseConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Errore nella connessione: " + e.getMessage());
        }
    }

    public void addBorrowing(int userId, int bookId, Date startDate, Date endDate) throws SQLException {
        String sql = "INSERT INTO borrowings (user_id, book_id, start_date, end_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.setDate(3, startDate);
            ps.setDate(4, endDate);
            ps.executeUpdate();
            System.out.println("Prestito aggiunto correttamente.");
        }
    }

    public Borrowing getBorrowingById(int borrowingId) throws SQLException {
        String sql = "SELECT * FROM borrowings WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, borrowingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Borrowing(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("book_id"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date")
                    );
                }
            }
        }
        return null;
    }

    public ArrayList<Borrowing> getAllBorrowings() throws SQLException {
        ArrayList<Borrowing> borrowings = new ArrayList<>();
        String sql = "SELECT * FROM borrowings ORDER BY start_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                borrowings.add(new Borrowing(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date")
                ));
            }
        }
        return borrowings;
    }

    public void deleteBorrowing(int borrowingId) throws SQLException {
        String sql = "DELETE FROM borrowings WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, borrowingId);
            ps.executeUpdate();
            System.out.println("Prestito eliminato con successo.");
        }
    }

    public void updateEndDate(int borrowingId, Date newEndDate) throws SQLException {
        String sql = "UPDATE borrowings SET end_date = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, newEndDate);
            ps.setInt(2, borrowingId);
            ps.executeUpdate();
            System.out.println("Data di fine prestito aggiornata con successo.");
        }
    }

    public ArrayList<Borrowing> getBorrowingsByUser(int userId) throws SQLException {
        ArrayList<Borrowing> borrowings = new ArrayList<>();
        String sql = "SELECT * FROM borrowings WHERE user_id = ? ORDER BY start_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    borrowings.add(new Borrowing(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("book_id"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date")
                    ));
                }
            }
        }
        return borrowings;
    }
}