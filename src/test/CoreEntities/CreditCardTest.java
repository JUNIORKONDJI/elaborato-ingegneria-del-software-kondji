package test.coreentities.librarymanagement;

import main.java.coreentities.librarymanagement.Book;
import main.java.coreentities.librarymanagement.CreditCard;
import main.java.coreentities.librarymanagement.User;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CreditCardTest {

    @Test
    void payTest() {
        User user = new User(1, "Alice", "Bianchi", 25, "alice.bianchi",
                "alice.bianchi@gmail.com", "SecurePass1", "Credit Card",
                "987654321", "2029-06-30", "999");
        Book book = new Book("Clean Code", "Robert C. Martin", "Pearson", "2025-01-01", true, 25.0f, 5);

        String userInput = "yes\n999\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputBuffer));

        user.getPaymentMethod().pay(book);

        System.setOut(originalOut);
        String output = outputBuffer.toString();

        assertTrue(output.contains("Paying for book: Clean Code"));
        assertTrue(output.contains("Amount: 25.25€")); // includes 1% commission
        assertTrue(output.contains("Payment successful!"));
    }

    @Test
    void refundTest() {
        User user = new User(1, "Alice", "Bianchi", 25, "alice.bianchi",
                "alice.bianchi@gmail.com", "SecurePass1", "Credit Card",
                "987654321", "2029-06-30", "999");
        Book book = new Book("Clean Code", "Robert C. Martin", "Pearson", "2025-01-01", true, 25.0f, 5);

        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputBuffer));

        user.getPaymentMethod().refund(book);

        System.setOut(originalOut);
        String output = outputBuffer.toString();

        assertTrue(output.contains("Refunding payment for book: Clean Code"));
        assertTrue(output.contains("Refund successful!"));
    }

    @Test
    void getPaymentMethodTest() {
        CreditCard card = new CreditCard("Alice", "Bianchi", "987654321", "2029-06-30", "999");
        assertEquals("Credit Card", card.getPaymentMethod());
    }

    @Test
    void getPaymentDataTest() {
        CreditCard card = new CreditCard("Alice", "Bianchi", "987654321", "2029-06-30", "999");
        String expected = "Owner: Alice Bianchi\nCard number: 987654321\nExpiration date: 2029-06-30\nSecurity code: 999";
        assertEquals(expected, card.getPaymentData());
    }
}
