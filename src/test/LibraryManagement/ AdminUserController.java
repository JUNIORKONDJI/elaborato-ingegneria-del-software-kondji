package test.LibraryManagement;

import main.java.LibraryManagement.AdminUserController;
import main.java.LibraryManagement.DatabaseResetController;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AdminUserControllerTest {

    @Test
    void displayAllRequestsTest() {
        // TODO: implement this test
    }

    @Test
    void deleteRequestByIdTest() {
        // TODO: implement this test
    }

    @Test
    void clearAllRequestsTest() {
        // TODO: implement this test
    }

    @Test
    void showAllBooksTest() {
        // TODO: implement this test
    }

    @Test
    void insertBookTest() {
        // TODO: implement this test
    }

    @Test
    void updateBookTest() {
        // TODO: implement this test
    }

    @Test
    void deleteBookTest() throws SQLException, ClassNotFoundException {
        AdminUserController adminUserController = new AdminUserController();

        // Scenario 1: Book is not eligible for return
        int bookId = 1;

        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outBuffer));

        adminUserController.deleteBook(bookId);

        System.setOut(originalOut);
        String result1 = outBuffer.toString();
        assertTrue(result1.contains("Book cannot be refunded, proceeding with deletion."
                + "\nLoan removed successfully."
                + "\nLoan removed successfully."
                + "\nLoan removed successfully."
                + "\nLoan removed successfully."
                + "\nLoan removed successfully."
                + "\nBook deleted successfully."
                + "\nBook deletion completed."));

        // Scenario 2: Book is refundable, but not borrowed
        String title = "Sample Book";
        String author = "Sample Author";
        String publisher = "Sample Publisher";
        String publishDate = "2030-01-01";
        boolean isReturnable = true;
        float price = 20;

        int newBookId = 99;

        adminUserController.insertBook(title, author, publisher, publishDate, isReturnable, price);

        ByteArrayOutputStream outBuffer2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outBuffer2));

        adminUserController.deleteBook(newBookId);

        System.setOut(originalOut);
        String result2 = outBuffer2.toString();
        assertTrue(result2.contains("Book deleted successfully."
                + "\nBook deletion completed."));

        // Scenario 3: Book is refundable and currently loaned
        int bookIdLoaned = 7;

        ByteArrayOutputStream outBuffer3 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outBuffer3));

        adminUserController.deleteBook(bookIdLoaned);

        System.setOut(originalOut);
        String result3 = outBuffer3.toString();
        assertTrue(result3.contains("Loan removed successfully."
                + "\nBook deleted successfully."
                + "\nBook deletion completed."));

        // Restore initial database state
        DatabaseResetController resetController = new DatabaseResetController();
        resetController.restoreInitialState();
    }

    @Test
    void searchBooksTest() {
        // TODO: implement this test
    }

    @Test
    void listAllBorrowingsTest() {
        // TODO: implement this test
    }
}
