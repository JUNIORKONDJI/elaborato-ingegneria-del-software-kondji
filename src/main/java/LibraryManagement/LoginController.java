package main.java.BusinessLogic;

import coreentities.librarymanagement.User;
import main.java.ORM.UserDAO;

import java.sql.SQLException;

public class LoginController {

    public LoginController() {}

    public User login(String username, String password) throws SQLException, ClassNotFoundException {
        UserDAO userDAO = new UserDAO();
        return userDAO.checkPassword(username, password);
    }

    public User register(String name, String surname, int age, String username, String email, String password,
                         String paymentMethod, String data1, String data2, String data3) throws SQLException, ClassNotFoundException {
        UserDAO userDAO = new UserDAO();
        return userDAO.registerUser(name, surname, age, username, email, password, paymentMethod, data1, data2, data3);
    }

    public String adminLogin(String password) {
        if (password.equals("admin")) {
            return "AdminLoggedIn";
        }
        System.out.println("Mot de passe incorrect.");
        return null;
    }
}



