package test.LibraryManagement;

import main.java.LibraryManagement.AdminExtraController;
import main.java.LibraryManagement.LoginController;
import main.java.LibraryManagement.UserProfileController;
import main.java.DomainModel.User;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileControllerTest {

    @Test
    void viewProfileTest() {
        // TODO: implement this test
    }

    @Test
    void updateNameTest() {
        // TODO: implement this test
    }

    @Test
    void updateSurnameTest() {
        // TODO: implement this test
    }

    @Test
    void updateAgeTest() {
        // TODO: implement this test
    }

    @Test
    void updateUsernameTest() {
        // TODO: implement this test
    }

    @Test
    void updateEmailTest() {
        // TODO: implement this test
    }

    @Test
    void updatePasswordTest() {
        // TODO: implement this test
    }

    @Test
    void setPaymentMethodTest() {
        // TODO: implement this test
    }

    @Test
    void updateCreditCardTest() {
        // TODO: implement this test
    }

    @Test
    void updatePaypalTest() {
        // TODO: implement this test
    }

    @Test
    void removePaymentMethodTest() throws SQLException, ClassNotFoundException {
        LoginController loginController = new LoginController();

        // Caso 1: utente con metodo di pagamento
        String username = "giulia.bianchi";
        String password = "SecurePass123";

        User user = loginController.login(username, password);
        UserProfileController userProfileController = new UserProfileController(user);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        userProfileController.removePaymentMethod();

        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("\nPayment method removed successfully!"));

        // Caso 2: utente senza metodo di pagamento
        String newUsername = "utenteTest";
        String newEmail = "utentetest@email.com";
        String newPassword = "nuovoTest123";

        User user2 = loginController.register(newUsername, newEmail, newPassword);
        UserProfileController userProfileController2 = new UserProfileController(user2);

        ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        PrintStream originalOut2 = System.out;
        System.setOut(new PrintStream(outContent2));

        userProfileController2.removePaymentMethod();

        System.setOut(originalOut2);
        String output2 = outContent2.toString();
        assertTrue(output2.contains("You do not have a payment method."));

        // Ripristina lo stato del DB
        AdminExtraController adminExtraController = new AdminExtraController();
        adminExtraController.generateDefaultDatabase();
    }
}
