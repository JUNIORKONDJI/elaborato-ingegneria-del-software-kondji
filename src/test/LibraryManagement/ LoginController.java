package test.LibraryManagaement;

import main.java.LibraryManagaement.LoginController;
import main.java.LibraryManagaement.AdminExtraController;
import main.java.CoreEntities.User;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @Test
    void loginTest() throws SQLException, ClassNotFoundException {

        LoginController loginController = new LoginController();

        // Cas de test 1 : utilisateur connu
        String username = "nicolas.dupont";
        String password = "SecurePass123";

        try {
            User user = loginController.login(username, password);
            assertNotNull(user, "L'utilisateur devrait être trouvé avec des identifiants valides.");
        } catch (SQLException | ClassNotFoundException e) {
            fail("Exception levée lors de la tentative de connexion : " + e.getMessage());
        }

        // Cas de test 2 : utilisateur inconnu
        String unknownUsername = "fake.user";
        String unknownPassword = "FakePassword";

        try {
            User unknownUser = loginController.login(unknownUsername, unknownPassword);
            assertNull(unknownUser, "Aucun utilisateur ne devrait être trouvé.");
        } catch (SQLException | ClassNotFoundException e) {
            fail("Exception levée pour un utilisateur inconnu : " + e.getMessage());
        }
    }

    @Test
    void registerTest() throws SQLException, ClassNotFoundException {

        LoginController loginController = new LoginController();

        String name = "Camille";
        String surname = "Moreau";
        int age = 27;
        String username = "camille.moreau";
        String email = "camille.moreau@mail.com";
        String password = "CamPass2025";
        String paymentMethod = "CreditCard";
        String cardNumber = "4000123456789010";
        String cardExpirationDate = "2031-06-30";
        String cardSecurityCode = "321";

        try {
            User newUser = loginController.register(name, surname, age, username, email, password, paymentMethod, cardNumber, cardExpirationDate, cardSecurityCode);
            assertNotNull(newUser, "L'inscription devrait créer un nouvel utilisateur.");
        } catch (SQLException | ClassNotFoundException e) {
            fail("Erreur lors de l'inscription : " + e.getMessage());
        }

        // Réinitialisation de la base après le test
        AdminExtraController adminExtraController = new AdminExtraController();
        adminExtraController.generateDefaultDatabase();
    }

    @Test
    void adminLoginTest() {
        LoginController loginController = new LoginController();

        String password = "admin";

        try {
            User adminUser = loginController.adminLogin(password);
            assertNotNull(adminUser, "L'administrateur devrait pouvoir se connecter avec le mot de passe correct.");
        } catch (SQLException | ClassNotFoundException e) {
            fail("Erreur lors de la connexion admin : " + e.getMessage());
        }
    }
}
