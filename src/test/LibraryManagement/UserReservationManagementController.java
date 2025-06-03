package test.LibraryManagement;

import main.java.LibraryManagement.AdminExtraController;
import main.java.LibraryManagement.LoginController;
import main.java.LibraryManagement.UserReservationManagementController;
import main.java.DomainModel.User;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserReservationManagementControllerTest {

    @Test
    void reserveBookTest() throws SQLException, ClassNotFoundException {
        String username = "mario.rossi";
        String password = "Password1";

        LoginController loginController = new LoginController();
        User user = loginController.login(username, password);

        UserReservationManagementController reservationController = new UserReservationManagementController(user);

        // Scenario 1: prenotazione valida
        int bookId = 1;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        reservationController.reserveBook(bookId);

        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("Reservation completed successfully."));

        // Scenario 2: libro già prenotato dallo stesso utente
        ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent2));

        reservationController.reserveBook(bookId);

        System.setOut(originalOut);
        String output2 = outContent2.toString();
        assertTrue(output2.contains("You have already reserved this book."));

        // Scenario 3: libro non esistente
        int invalidBookId = 999;

        ByteArrayOutputStream outContent3 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent3));

        reservationController.reserveBook(invalidBookId);

        System.setOut(originalOut);
        String output3 = outContent3.toString();
        assertTrue(output3.contains("Book not found."));

        // Reset DB
        AdminExtraController adminExtraController = new AdminExtraController();
        adminExtraController.generateDefaultDatabase();
    }

    @Test
    void viewMyReservationsTest() {
        // TODO: implement this test
    }

    @Test
    void cancelReservationTest() throws SQLException, ClassNotFoundException {
        String username = "mario.rossi";
        String password = "Password1";

        LoginController loginController = new LoginController();
        User user = loginController.login(username, password);

        UserReservationManagementController reservationController = new UserReservationManagementController(user);

        // Scenario 1: prenotazione esistente
        int reservationId = 2;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        reservationController.cancelReservation(reservationId);

        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("Reservation cancelled successfully."));

        // Scenario 2: prenotazione inesistente
        int invalidReservationId = 999;

        ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent2));

        reservationController.cancelReservation(invalidReservationId);

        System.setOut(originalOut);
        String output2 = outContent2.toString();
        assertTrue(output2.contains("Reservation not found."));

        // Reset DB
        AdminExtraController adminExtraController = new AdminExtraController();
        adminExtraController.generateDefaultDatabase();
    }
}
