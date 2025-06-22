package main.java.LibraryManagement;

import main.java.CoreEntities.User;
import main.java.ORM.UserDAO;

import java.sql.SQLException;
import java.util.List;

public class AdminUserController {

    private final UserDAO userDAO;

    public AdminUserController() {
        this.userDAO = new UserDAO();
    }

    public List<User> viewUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    public User searchUser(String username) throws SQLException {
        return userDAO.getUserByUsername(username);
    }

    public void addUser(String name, String surname, int age, String username, String email, String password,
                        String paymentMethod, String cardNumber, String expirationDate, String securityCode)
            throws SQLException {

        User user = new User(name, surname, age, username, email, password,
                paymentMethod, cardNumber, expirationDate, securityCode);
        userDAO.addUser(user);
        System.out.println("✅ Utilisateur ajouté avec succès.");
    }

    public void removeUser(int userId) throws SQLException {
        userDAO.removeUser(userId);
        System.out.println("❌ Utilisateur supprimé.");
    }
}
