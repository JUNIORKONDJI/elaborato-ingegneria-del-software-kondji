package test.BusinessLogic;

import main.java.BusinessLogic.AdminBookController;
import main.java.BusinessLogic.AdminExtraController;
import main.java.BusinessLogic.AdminUserController;
import main.java.DomainModel.Book;
import main.java.DomainModel.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdminExtraController {

    @Test
    void resetPasswordTest() {
        // Da implementare: verifica che la password dell'admin venga aggiornata correttamente
    }

    @Test
    void resetDatabaseTest() {
        // Da implementare: verifica che il database venga cancellato e ricreato
    }

    @Test
    void generateDefaultDatabaseTest() throws SQLException, ClassNotFoundException {

        AdminUserController adminUserController = new AdminUserController();
        AdminBookController adminBookController = new AdminBookController();
        AdminExtraController adminExtraController = new AdminExtraController();

        // Dati utente di esempio
        String name = "Alice";
        String surname = "Wonderland";
        int age = 28;
        String username = "alice.user";
        String email = "alice@example.com";
        String password = "StrongPass123";
        String paymentMethod = "PayPal";
        String paypalEmail = "alice@paypal.com";
        String paypalPassword = "paypalSecure";

        ArrayList<User> users = adminUserController.searchUser(username);
        assertTrue(users == null || users.isEmpty());

        adminUserController.addUser(name, surname, age, username, email, password, paymentMethod, paypalEmail, paypalPassword);

        assertEquals(1, adminUserController.searchUser(username).size());

        // Dati libro di esempio
        String title = "The Book of Testing";
        String author = "Test Author";
        int year = 2030;
        String genre = "Testing";
        String description = "A guide to unit testing.";
        float price = 15.5f;
        boolean refundable = true;

        ArrayList<Book> books = adminBookController.searchBook(title);
        assertTrue(books == null || books.isEmpty());

        adminBookController.addBook(title, author, year, genre, description, refundable, price);

        assertEquals(1, adminBookController.searchBook(title).size());

        // Reset e inizializzazione del DB
        adminExtraController.generateDefaultDatabase();

        // Verifica che gli utenti e i libri siano stati eliminati
        ArrayList<User> usersAfter = adminUserController.searchUser(username);
        assertTrue(usersAfter == null || usersAfter.isEmpty());

        ArrayList<Book> booksAfter = adminBookController.searchBook(title);
        assertTrue(booksAfter == null || booksAfter.isEmpty());
    }
}
