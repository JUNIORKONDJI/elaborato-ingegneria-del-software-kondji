package LibraryManagement;

import CoreEntities.Book;
import CoreEntities.Reservation;
import CoreEntities.User;
import ORM.BookDAO;
import ORM.ReservationDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBookPageController {

    private final User user;

    public UserBookPageController(User user) {
        this.user = user;
    }

    // 📚 Affiche tous les livres disponibles
    public ArrayList<Book> viewBooks() throws SQLException {
        BookDAO bookDAO = new BookDAO();
        return bookDAO.getAllBooks();
    }

    // 🔍 Recherche un livre selon un critère
    public ArrayList<Book> searchBook(String searchBy, String data) throws SQLException {
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> books = new ArrayList<>();

        switch (searchBy) {
            case "isbn" -> {
                Book book = bookDAO.getBook(Integer.parseInt(data));
                if (book != null) books.add(book);
            }
            case "title" -> books = bookDAO.getBooksByTitle(data);
            case "author" -> books = bookDAO.getBooksByAuthor(data);
            case "year" -> books = bookDAO.getBooksByYear(Integer.parseInt(data));
        }

        return books;
    }

    // 📝 Réserve un livre
    public void reserveBook(int bookId) throws SQLException {
        BookDAO bookDAO = new BookDAO();
        ReservationDAO reservationDAO = new ReservationDAO();

        Book book = bookDAO.getBook(bookId);
        if (book == null) {
            System.out.println("Livre introuvable.");
            return;
        }

        if (user.getPaymentMethod() == null) {
            System.out.println("Vous devez enregistrer un moyen de paiement.");
            return;
        }

        if (reservationDAO.getReservation(user.getId(), bookId) != null) {
            System.out.println("Vous avez déjà réservé ce livre.");
            return;
        }

        if (book.getCreatedBy() == user.getId()) {
            System.out.println("Vous ne pouvez pas réserver votre propre livre.");
            return;
        }

        user.getPaymentMethod().pay(book);
        reservationDAO.addReservation(user.getId(), bookId, user.getPaymentMethodType());

        System.out.println("Réservation effectuée.");
    }

    // 👁️‍🗨️ Affiche tous les livres que l’utilisateur a réservés
    public ArrayList<Book> viewReservedBooks() throws SQLException {
        ReservationDAO reservationDAO = new ReservationDAO();
        ArrayList<Book> reservedBooks = new ArrayList<>();

        for (Reservation reservation : reservationDAO.getReservationsByUser(user.getId())) {
            reservedBooks.add(reservation.getBook());
        }

        return reservedBooks;
    }

    // ❌ Annule une réservation (et rembourse si applicable)
    public void cancelReservation(int bookId) throws SQLException {
        ReservationDAO reservationDAO = new ReservationDAO();
        Reservation reservation = reservationDAO.getReservation(user.getId(), bookId);

        if (reservation == null) {
            System.out.println("Vous n’avez pas réservé ce livre.");
            return;
        }

        Book book = reservation.getBook();

        if (!book.isPaymentRequired() || !book.isRefundable()) {
            System.out.println("Aucun remboursement prévu. Suppression de la réservation...");
        }

        reservationDAO.removeReservation(user.getId(), bookId);

        if (book.getPrice() > 0 && book.isRefundable() && user.getPaymentMethod() != null) {
            user.getPaymentMethod().refund(book);
            System.out.println("Vous avez été remboursé.");
        }

        System.out.println("Réservation annulée.");
    }
}
