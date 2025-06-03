package main.java.BusinessLogic;

import main.java.DomainModel.User;
import main.java.ORM.UserDAO;

import java.sql.SQLException;

public class LoginController {

    public LoginController() {}

    public User login(String username, String password) throws SQLException, ClassNotFoundException {

        UserDAO userDAO = new UserDAO();

        return userDAO.checkPassword(username, password);

    }

    public User register(String name, String surname, int age, String username, String email, String password, String paymentMethod, String cardNumberORuniqueCode, String cardExpirationDateORaccountEmail, String cardSecurityCodeORaccountPassword) throws SQLException, ClassNotFoundException {

        UserDAO userDAO = new UserDAO();

        userDAO.addUser(name, surname, age, username, email, password, paymentMethod, cardNumberORuniqueCode, cardExpirationDateORaccountEmail, cardSecurityCodeORaccountPassword);

        return userDAO.checkPassword(username, password);

        // FIXME: if the string is empty, is it better to set it to null or to an empty string?

    }

    public User register(String username, String email, String password) throws SQLException, ClassNotFoundException {

        UserDAO userDAO = new UserDAO();

        userDAO.addUser(username, email, password);

        return userDAO.checkPassword(username, password);

    }

    public User adminLogin(String password) throws SQLException, ClassNotFoundException {

        UserDAO userDAO = new UserDAO();

        return userDAO.checkPassword("ADMIN",password);

    }

}