package test.java;

import coreentities.librarymanagement.Book;
import coreentities.librarymanagement.BookDAO;

import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDAOTest {

    private static BookDAO bookDAO;
    private static Book testBook;

    @BeforeAll
    public static void setup() {
        bookDAO = new BookDAO();
        testBook = new Book("JUnit Book", "Test description", "JUnit Author", 2025, 29.99f, true);
    }

    @Test
    @Order(1)
    public void testAddBook() {
        boolean success = bookDAO.addBook(testBook);
        assertTrue(success, "Failed to add book");
    }

    @Test
    @Order(2)
    public void testGetAllBooks() {
        List<Book> books = bookDAO.getAllBooks();
        assertNotNull(books, "Books list should not be null");
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("JUnit Book")), "Test book not found");
    }

    @Test
    @Order(3)
    public void testGetBookByISBN() {
        Book fetched = bookDAO.getBookByTitle("JUnit Book");
        assertNotNull(fetched, "Fetched book should not be null");
        assertEquals("JUnit Author", fetched.getAuthor(), "Author does not match");
    }

    @Test
    @Order(4)
    public void testUpdateBook() {
        Book fetched = bookDAO.getBookByTitle("JUnit Book");
        fetched.setPrice(34.99f);
        boolean updated = bookDAO.updateBook(fetched);
        assertTrue(updated, "Update failed");
        Book updatedBook = bookDAO.getBookByISBN(fetched.getIsbn());
        assertEquals(34.99f, updatedBook.getPrice(), 0.001, "Price not updated");
    }

    @Test
    @Order(5)
    public void testDeleteBook() {
        Book fetched = bookDAO.getBookByTitle("JUnit Book");
        boolean deleted = bookDAO.deleteBook(fetched.getIsbn());
        assertTrue(deleted, "Delete failed");
        Book shouldBeNull = bookDAO.getBookByISBN(fetched.getIsbn());
        assertNull(shouldBeNull, "Book should be null after deletion");
    }
}
