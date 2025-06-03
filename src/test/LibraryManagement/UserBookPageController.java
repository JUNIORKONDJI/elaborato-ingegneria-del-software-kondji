package test.LibraryManagement;

import main.java.LibraryManagement.AdminExtraController;
import main.java.LibraryManagement.LoginController;
import main.java.LibraryManagement.UserBookPageController;

import main.java.DomainModel.User;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserBookPageControllerTest {

    @Test
    void addBookTest() throws SQLException, ClassNotFoundException {

        String username = "mario.rossi";
        String password = "Password1";

        LoginController loginController = new LoginController();
        User user = loginController.login(username, password);

        UserBookPageController userBookPageController = new UserBookPageController(user);

        // Test case 1: valid book addition
        String title = "Test Title";
        String author = "Test Author";
        String publisher = "Test Publisher";
        String publishDate = "2030-01-01";
        boolean isReturnable = true;
        float price = 10;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            userBookPageController.addBook(title, author, publisher, publishDate, isReturnable, price);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("Book added successfully."));

        // Test case 2: duplicate title
        String title2 = "BookTrack Special";
        String author2 = "Author 2";
        String publisher2 = "Publisher 2";
        String publishDate2 = "2030-01-01";
        boolean isReturnable2 = true;
        float price2 = 12;

        ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        PrintStream originalOut2 = System.out;
        System.setOut(new PrintStream(outContent2));

        try {
            userBookPageController.addBook(title2, author2, publisher2, publishDate2, isReturnable2, price2);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.setOut(originalOut2);
        String output2 = outContent2.toString();
        assertTrue(output2.contains("A book with the same title already exists."));

        // Reset
        AdminExtraController adminExtraController = new AdminExtraController();
        adminExtraController.generateDefaultDatabase();
    }

    @Test
    void viewCreatedBooksTest() {
        // TODO: implement this test
    }

    @Test
    void viewBookBorrowersTest() {
        // TODO: implement this test
    }

    @Test
    void updateBookDescriptionTest() {
        // TODO: implement this test
    }

    @Test
    void requestToEditBookAttributesTest() {
        // TODO: implement this test
    }

    @Test
    void requestBookCancellationTest() throws SQLException, ClassNotFoundException {

        String username = "mario.rossi";
        String password = "Password1";

        LoginController loginController = new LoginController();
        User user = loginController.login(username, password);

        UserBookPageController userBookPageController = new UserBookPageController(user);

        // Test case 1: user owns the book
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        userBookPageController.requestBookCancellation(1);

        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("Request submitted." + "\nCancellation request saved."));

        // Test case 2: user doesn't own the book
        ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        PrintStream originalOut2 = System.out;
        System.setOut(new PrintStream(outContent2));

        userBookPageController.requestBookCancellation(2);

        System.setOut(originalOut2);
        String output2 = outContent2.toString();
        assertTrue(output2.contains("You are not the owner of this book."));

        // Test case 3: book doesn't exist
        ByteArrayOutputStream outContent3 = new ByteArrayOutputStream();
        PrintStream originalOut3 = System.out;
        System.setOut(new PrintStream(outContent3));

        userBookPageController.requestBookCancellation(999);

        System.setOut(originalOut3);
        String output3 = outContent3.toString();
        assertTrue(output3.contains("Book not found."));

        // Reset DB
        AdminExtraController adminExtraController = new AdminExtraController();
        adminExtraController.generateDefaultDatabase();
    }
}
