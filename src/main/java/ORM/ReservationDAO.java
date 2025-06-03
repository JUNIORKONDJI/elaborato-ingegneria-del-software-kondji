package coreentities.librarymanagement;

import java.sql.*;
import java.util.ArrayList;

public class ReservationDAO {

    private Connection connection;

    public ReservationDAO() {
        try {
            this.connection = DatabaseConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Errore nella connessione: " + e.getMessage());
        }
    }

    public void addReservation(int userId, int bookId, Date reservationDate, String status) throws SQLException {
        String sql = "INSERT INTO reservations (user_id, book_id, reservation_date, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.setDate(3, reservationDate);
            ps.setString(4, status);
            ps.executeUpdate();
            System.out.println("Prenotazione aggiunta con successo.");
        }
    }

    public Reservation getReservationById(int reservationId) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, reservationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Reservation(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("book_id"),
                            rs.getDate("reservation_date"),
                            rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    public ArrayList<Reservation> getAllReservations() throws SQLException {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations ORDER BY reservation_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getDate("reservation_date"),
                        rs.getString("status")
                ));
            }
        }
        return reservations;
    }

    public ArrayList<Reservation> getReservationsByUserId(int userId) throws SQLException {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservations.add(new Reservation(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("book_id"),
                            rs.getDate("reservation_date"),
                            rs.getString("status")
                    ));
                }
            }
        }
        return reservations;
    }

    public void updateReservationStatus(int reservationId, String newStatus) throws SQLException {
        String sql = "UPDATE reservations SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, reservationId);
            ps.executeUpdate();
            System.out.println("Stato della prenotazione aggiornato con successo.");
        }
    }

    public void deleteReservation(int reservationId) throws SQLException {
        String sql = "DELETE FROM reservations WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, reservationId);
            ps.executeUpdate();
            System.out.println("Prenotazione eliminata con successo.");
        }
    }

    public void deleteAllReservationsByUser(int userId) throws SQLException {
        String sql = "DELETE FROM reservations WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
            System.out.println("Tutte le prenotazioni dell’utente sono state eliminate.");
        }
    }

    public void deleteAllReservationsByBook(int bookId) throws SQLException {
        String sql = "DELETE FROM reservations WHERE book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ps.executeUpdate();
            System.out.println("Tutte le prenotazioni per il libro sono state eliminate.");
        }
    }

    public ArrayList<Reservation> getReservationsByStatus(String status) throws SQLException {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE status = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservations.add(new Reservation(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("book_id"),
                            rs.getDate("reservation_date"),
                            rs.getString("status")
                    ));
                }
            }
        }
        return reservations;
    }

    public boolean checkReservationExists(int userId, int bookId) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE user_id = ? AND book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int countReservationsByUser(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM reservations WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    public int countReservationsByBook(int bookId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM reservations WHERE book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    public Date getLatestReservationDate(int userId) throws SQLException {
        String sql = "SELECT MAX(reservation_date) AS latest FROM reservations WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDate("latest");
                }
            }
        }
        return null;
    }

    public void cancelReservation(int reservationId) throws SQLException {
        updateReservationStatus(reservationId, "Cancelled");
    }

    public ArrayList<Integer> getAllReservationIds() throws SQLException {
        ArrayList<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM reservations ORDER BY id ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        }
        return ids;
    }
}
