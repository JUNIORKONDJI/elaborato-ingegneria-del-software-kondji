package test.java;

import coreentities.librarymanagement.*;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BorrowingDAOTest {

    private static BorrowingDAO borrowingDAO;
    private static UserDAO userDAO;
    private static BookDAO bookDAO;

    private static User testUser;
    private static Book testBook;

    @BeforeAll
    public static void setup() {
        borrowingDAO = new BorrowingDAO();
        userDAO = new UserDAO();
        bookDAO = new BookDAO();

        testUser = new User("Borrow", "Tester", 22, "borrow_user", "borrow@test.com", "pass123");
        testBook = new Book("BorrowBook", "Used for borrowing tests", "BorrowAuthor", 2025, 19.99f, true);

        userDAO.addUser(testUser);
        Book addedBook = bookDAO.getBookByTitle("BorrowBook");
        if (addedBook == null) bookDAO.addBook(testBook);

        // Refresh IDs
        testUser = userDAO.getUserByEmail("borrow@test.com");
        testBook = bookDAO.getBookByTitle("BorrowBook");
    }

    @Test
    @Order(1)
    public void testAddBorrowing() {
        boolean added = borrowingDAO.addBorrowing(testUser.getId(), testBook.getIsbn(), "Credit Card");
        assertTrue(added, "Borrowing should be added successfully");
    }

    @Test
    @Order(2)
    public void testGetBorrowingsByUser() {
        List<Book> books = borrowingDAO.getBorrowedBooksByUserId(testUser.getId());
        assertNotNull(books, "Returned list should not be null");
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("BorrowBook")), "Test book not found in borrowings");
    }

    @Test
    @Order(3)
    public void testRemoveBorrowing() {
        boolean removed = borrowingDAO.removeBorrowing(testUser.getId(), testBook.getIsbn());
        assertTrue(removed, "Borrowing should be removed successfully");

        List<Book> booksAfter = borrowingDAO.getBorrowedBooksByUserId(testUser.getId());
        assertTrue(booksAfter.stream().noneMatch(b -> b.getIsbn() == testBook.getIsbn()), "Book should not be in borrowings anymore");
    }

    @AfterAll
    public static void tearDown() {
        borrowingDAO.removeBorrowing(testUser.getId(), testBook.getIsbn());
        userDAO.deleteUser(testUser.getId());
        bookDAO.deleteBook(testBook.getIsbn());
    }
}
