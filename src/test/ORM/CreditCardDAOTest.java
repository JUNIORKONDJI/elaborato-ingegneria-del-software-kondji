package test.java;

import coreentities.librarymanagement.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditCardDAOTest {

    private static UserDAO userDAO;
    private static CreditCardDAO creditCardDAO;

    private static User testUser;

    @BeforeAll
    public static void setup() {
        userDAO = new UserDAO();
        creditCardDAO = new CreditCardDAO();

        testUser = new User("Card", "Tester", 30, "card_user", "card@test.com", "passcard");
        userDAO.addUser(testUser);

        testUser = userDAO.getUserByEmail("card@test.com");
    }

    @Test
    @Order(1)
    public void testAddCreditCard() {
        CreditCard card = new CreditCard("Card", "Tester", "1111222233334444", "12/27", "321");
        boolean added = creditCardDAO.addCreditCard(testUser.getId(), card);
        assertTrue(added, "Credit card should be added successfully");
    }

    @Test
    @Order(2)
    public void testGetCreditCardByUserId() {
        CreditCard card = creditCardDAO.getCreditCardByUserId(testUser.getId());
        assertNotNull(card, "Credit card should be retrieved");
        assertEquals("1111222233334444", card.getCardNumber(), "Card number should match");
        assertEquals("12/27", card.getCardExpirationDate());
        assertEquals("321", card.getCardSecurityCode());
    }

    @Test
    @Order(3)
    public void testDeleteCreditCard() {
        boolean deleted = creditCardDAO.deleteCreditCardByUserId(testUser.getId());
        assertTrue(deleted, "Credit card should be deleted successfully");

        CreditCard cardAfter = creditCardDAO.getCreditCardByUserId(testUser.getId());
        assertNull(cardAfter, "Credit card should no longer exist");
    }

    @AfterAll
    public static void tearDown() {
        userDAO.deleteUser(testUser.getId());
    }
}
