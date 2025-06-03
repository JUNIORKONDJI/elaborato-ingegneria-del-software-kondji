package test.coreentities.librarymanagement;

import main.java.coreentities.librarymanagement.PayPal;
import main.java.coreentities.librarymanagement.User;
import main.java.coreentities.librarymanagement.Book;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class PayPalTest {

    @Test
    void payTest() {
        User user = new User(3, "Giovanni", "Verdi", 25, "gio.verdi7",
                "gio.verdi7@gmail.com", "giovanni7", "PayPal", "1", "gio.verdi7@gmail.com", "gio7");

        Book book = new Book(5, "Advanced Algorithms", "Computer Science", "Cambridge Press", "2030-06-15", true, 25.00f);

        System.out.println("PayPalTest: payTest()");

        String simulatedInput = "yes\ngio7\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        user.getPaymentMethod().pay(book);

        System.setOut(originalOut);
        String output = out.toString();

        assertTrue(output.contains("Paying for book: " + book.getTitle()));
        assertTrue(output.contains("Payment successful!"));
        System.out.println();
    }

    @Test
    void refundTest() {
        User user = new User(3, "Giovanni", "Verdi", 25, "gio.verdi7",
                "gio.verdi7@gmail.com", "giovanni7", "PayPal", "1", "gio.verdi7@gmail.com", "gio7");

        Book book = new Book(5, "Advanced Algorithms", "Computer Science", "Cambridge Press", "2030-06-15", true, 25.00f);

        System.out.println("PayPalTest: refundTest()");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        user.getPaymentMethod().refund(book);

        System.setOut(originalOut);
        String output = out.toString();

        assertTrue(output.contains("Refunding the payment..."));
        assertTrue(output.contains("Refund successful!"));
        System.out.println();
    }

    @Test
    void getPaymentMethodTest() {
        PayPal payPal = new PayPal("Giovanni", "Verdi", "1", "gio.verdi7@gmail.com", "gio7");
        assertEquals("PayPal", payPal.getPaymentMethod());
    }

    @Test
    void getPaymentDataTest() {
        PayPal payPal = new PayPal("Giovanni", "Verdi", "1", "gio.verdi7@gmail.com", "gio7");
        assertEquals("Owner: Giovanni Verdi\nUnique code: 1\nAccount email: gio.verdi7@gmail.com\nAccount password: gio7", payPal.getPaymentData());
    }
}
