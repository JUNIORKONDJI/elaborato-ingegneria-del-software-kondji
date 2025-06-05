package test.java;

import coreentities.librarymanagement.*;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationDAOTest {

    private static Connection connection;
    private static User testUser;
    private static Book testBook;
    private static Reservation testReservation;

    @BeforeAll
    public static void setupDatabase() {
        try {
            connection = DatabaseConnectionManager.getConnection();

            UserDAO userDAO = new UserDAO(connection);
            BookDAO bookDAO = new BookDAO(connection);
            ReservationDAO reservationDAO = new ReservationDAO(connection);

            testUser = new User("Bob", "Builder", 32, "bobbuild", "bob@builder.com", "toolbox123");
            userDAO.save(testUser);

            testBook = new Book("9780000000011", "Builder's Guide", "Guide to construction", "Bob B.", 2023, 29.99f, true);
            bookDAO.save(testBook);

            testReservation = new Reservation(testUser.getId(), testBook.getIsbn(), "pending");
            reservationDAO.save(testReservation);

        } catch (SQLException e) {
            fail("Erreur d'initialisation de la base de données : " + e.getMessage());
        }
    }

    @Test
    public void testFindReservationByCode() {
        try {
            ReservationDAO reservationDAO = new ReservationDAO(connection);
            Reservation retrieved = reservationDAO.findByCode(testReservation.getCode());

            assertNotNull(retrieved, "La réservation ne doit pas être null");
            assertEquals("pending", retrieved.getStatus());
            assertEquals(testUser.getId(), retrieved.getUserId());
            assertEquals(testBook.getIsbn(), retrieved.getBookIsbn());
        } catch (SQLException e) {
            fail("Erreur dans testFindReservationByCode : " + e.getMessage());
        }
    }

    @Test
    public void testUpdateReservationStatus() {
        try {
            ReservationDAO reservationDAO = new ReservationDAO(connection);

            testReservation.setStatus("confirmed");
            reservationDAO.update(testReservation);

            Reservation updated = reservationDAO.findByCode(testReservation.getCode());
            assertEquals("confirmed", updated.getStatus());
        } catch (SQLException e) {
            fail("Erreur dans testUpdateReservationStatus : " + e.getMessage());
        }
    }

    @Test
    public void testDeleteReservation() {
        try {
            ReservationDAO reservationDAO = new ReservationDAO(connection);
            reservationDAO.delete(testReservation.getCode());

            Reservation deleted = reservationDAO.findByCode(testReservation.getCode());
            assertNull(deleted, "La réservation doit avoir été supprimée");
        } catch (SQLException e) {
            fail("Erreur dans testDeleteReservation : " + e.getMessage());
        }
    }

    @AfterAll
    public static void cleanup() {
        try {
            BookDAO bookDAO = new BookDAO(connection);
            UserDAO userDAO = new UserDAO(connection);

            bookDAO.deleteByIsbn(testBook.getIsbn());
            userDAO.deleteById(testUser.getId());

            connection.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors du nettoyage : " + e.getMessage());
        }
    }
}
