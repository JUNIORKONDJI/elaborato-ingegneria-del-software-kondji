package test.BusinessLogic;

import main.java.BusinessLogic.AdminBookController;
import main.java.BusinessLogic.AdminExtraController;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AdminBookControllerTest {

    @Test
    void seeRequestsTest() {
        // TODO: implement this test
    }

    @Test
    void removeRequestTest() {
        // TODO: implement this test
    }

    @Test
    void removeAllRequestsTest() {
        // TODO: implement this test
    }

    @Test
    void viewBooksTest() {
        // TODO: implement this test
    }

    @Test
    void addBookTest() {
        // TODO: implement this test
    }

    @Test
    void editBookTest() {
        // TODO: implement this test
    }

    @Test
    void removeBookTest() throws SQLException, ClassNotFoundException {
        AdminBookController adminBookController = new AdminBookController();

        // Test 1: Book is not refundable
        int bookId = 1;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        adminBookController.removeBook(bookId);

        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("Book is not refundable, but it will be removed anyway." +
                "\nBorrowing removed successfully." +
                "\nBorrowing removed successfully." +
                "\nBorrowing removed successfully." +
                "\nBorrowing removed successfully." +
                "\nBorrowing removed successfully." +
                "\nBook removed successfully." +
                "\nBook removed."));

        // Test 2: Book is refundable, but has no borrowings
        String title = "Sample Title";
        String author = "Sample Author";
        String publisher = "Sample Publisher";
        String date = "2030-01-01";
        String time = "10:00";
        boolean refundable = true;
        float price = 25.0f;

        int bookId2 = 11;

        adminBookController.addBook(title, author, publisher, date, time, refundable, price);

        ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        PrintStream originalOut2 = System.out;
        System.setOut(new PrintStream(outContent2));

        adminBookController.removeBook(bookId2);

        System.setOut(originalOut2);
        String output2 = outContent2.toString();
        assertTrue(output2.contains("Book removed successfully.\nBook removed."));

        // Test 3: Book is refundable and has borrowings
        int bookId3 = 7;

        ByteArrayOutputStream outContent3 = new ByteArrayOutputStream();
        PrintStream originalOut3 = System.out;
        System.setOut(new PrintStream(outContent3));

        adminBookController.removeBook(bookId3);

        System.setOut(originalOut3);
        String output3 = outContent3.toString();
        assertTrue(output3.contains("Borrowing removed successfully." +
                "\nBook removed successfully." +
                "\nBook removed."));

        // Reset DB
        AdminExtraController adminExtraController = new AdminExtraController();
        adminExtraController.generateDefaultDatabase();
    }

    @Test
    void searchBooksTest() {
        // TODO: implement this test
    }

    @Test
    void viewBorrowingsTest() {
        // TODO: implement this test
    }
}
