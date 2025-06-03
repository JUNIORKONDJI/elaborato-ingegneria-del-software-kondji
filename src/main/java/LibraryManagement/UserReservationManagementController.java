package LibraryManagement;

import CoreEntities.User;
import CoreEntities.Book;
import CoreEntities.Reservation;
import ORM.BookDAO;
import ORM.ReservationDAO;
import ORM.RequestDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserReservationManagementController {

    private final User user;

    public UserReservationManagementController(User user) {
        this.user = user;
    }

    // 📌 Crée une nouvelle réservation pour un livre
    public void createReservation(int bookId) throws SQLException {
        ReservationDAO reservationDAO = new ReservationDAO();
        reservationDAO.addReservation(user.getId(), bookId, user.getPaymentMethod());
        System.out.println("Réservation enregistrée.");
    }

    // 📌 Affiche toutes les réservations créées par l’utilisateur
    public ArrayList<Reservation> viewCreatedReservations() throws SQLException {
        ReservationDAO reservationDAO = new ReservationDAO();
        return reservationDAO.getReservationsByUser(user.getId());
    }

    // 📌 Affiche les utilisateurs ayant réservé un livre que je gère (si je suis "propriétaire" du livre)
    public ArrayList<User> viewBookReservations(int bookId) throws SQLException {
        BookDAO bookDAO = new BookDAO();
        ReservationDAO reservationDAO = new ReservationDAO();

        Book book = bookDAO.getBook(bookId);
        if (book.getCreatedBy() != user.getId()) {
            System.out.println("Vous n’êtes pas le créateur de ce livre.");
            return null;
        }

        return reservationDAO.getUsersByBook(bookId);
    }

    // ✏️ Met à jour la description d’un livre
    public void updateBookDescription(int bookId, String newDescription) throws SQLException {
        BookDAO bookDAO = new BookDAO();
        bookDAO.updateDescription(bookId, newDescription);
        System.out.println("Description mise à jour.");
    }

    // 📨 Envoie une demande de modification des attributs
    public void requestToChangeBookAttributes(int bookId, String request) throws SQLException {
        BookDAO bookDAO = new BookDAO();
        RequestDAO requestDAO = new RequestDAO();

        if (bookDAO.getBook(bookId).getCreatedBy() != user.getId()) {
            System.out.println("Vous n’êtes pas le créateur de ce livre.");
            return;
        }

        requestDAO.addRequest(user.getId(), request);
        System.out.println("Requête envoyée.");
    }

    // ❌ Demande l’annulation d’un livre
    public void requestToCancelBook(int bookId) throws SQLException {
        BookDAO bookDAO = new BookDAO();
        RequestDAO requestDAO = new RequestDAO();

        Book book = bookDAO.getBook(bookId);
        if (book == null) {
            System.out.println("Livre introuvable.");
            return;
        }

        if (book.getCreatedBy() != user.getId()) {
            System.out.println("Vous n’êtes pas le créateur de ce livre.");
            return;
        }

        String request = "| ANNULATION | Livre Code: " + bookId + " |";
        requestDAO.addRequest(user.getId(), request);
        System.out.println("Requête d’annulation envoyée.");
    }
}
