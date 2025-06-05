package test.java.DAOTest;

import coreentities.librarymanagement.User;
import coreentities.librarymanagement.UserDAO;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private static UserDAO userDAO;

    @BeforeAll
    public static void setup() {
        userDAO = new UserDAO();
    }

    @Test
    public void testAddUserAndFindById() {
        User user = new User("John", "Doe", 30, "john_doe", "john@example.com", "password123");
        userDAO.addUser(user);

        User found = userDAO.findUserById(user.getId());
        assertNotNull(found);
        assertEquals(user.getUsername(), found.getUsername());
    }

    @Test
    public void testUpdateUser() {
        User user = new User("Jane", "Smith", 28, "jane_smith", "jane@example.com", "password456");
        userDAO.addUser(user);

        user.updateEmail("new_email@example.com");
        userDAO.updateUser(user);

        User updated = userDAO.findUserById(user.getId());
        assertEquals("new_email@example.com", updated.getEmail());
    }

    @Test
    public void testDeleteUser() {
        User user = new User("Alice", "Brown", 25, "alice_brown", "alice@example.com", "alicepass");
        userDAO.addUser(user);

        userDAO.deleteUser(user.getId());
        assertNull(userDAO.findUserById(user.getId()));
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = userDAO.getAllUsers();
        assertNotNull(users);
        assertTrue(users.size() >= 0);
    }
}
