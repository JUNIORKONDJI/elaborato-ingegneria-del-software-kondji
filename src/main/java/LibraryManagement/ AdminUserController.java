package LibraryManagement;

import CoreEntities.User;
import ORM.UserDAO;

import java.sql.SQLException;
import java.util.List;

public class AdminUserController {

    private final UserDAO userDAO;

    public AdminUserController() {
        this.userDAO = new UserDAO();
    }

    // 📄 Affiche tous les utilisateurs enregistrés
    public List<User> viewUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    // 🔍 Recherche un utilisateur par username
    public User searchUser(String username) throws SQLException {
        return userDAO.getUserByUsername(username);
    }

    // ➕ Ajoute un nouvel utilisateur
    public void addUser(String name, String surname, int age, String username, String email, String password) throws SQLException {
        User user = new User(name, surname, age, username, email, password);
        userDAO.addUser(user);
        System.out.println("Utilisateur ajouté avec succès.");
    }

    // 🗑️ Supprime un utilisateur par son ID
    public void removeUser(int userId) throws SQLException {
        userDAO.removeUser(userId);
        System.out.println("Utilisateur supprimé.");
    }
}
