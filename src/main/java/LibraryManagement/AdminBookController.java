package LibraryManagement;

import CoreEntities.Book;
import CoreEntities.Request;
import CoreEntities.Reservation;
import CoreEntities.User;
import ORM.BookDAO;
import ORM.RequestDAO;
import ORM.ReservationDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminUserController {

    // 📄 Affiche toutes les requêtes envoyées par les utilisateurs
    public ArrayList<Request> viewRequests() throws SQLException {
        RequestDAO requestDAO = new RequestDAO();
        return requestDAO.getAllRequests();
    }

    // 🗑️ Supprime une requête spécifique
    public void removeRequest(int requestId) throws SQLException {
        RequestDAO requestDAO = new RequestDAO();
        requestDAO.removeRequest(requestId);
        System.out.println("Requête supprimée avec succès.");
    }

    // 🧽 Supprime toutes les requêtes du système
    public void removeAllRequests() throws SQLException {
        RequestDAO requestDAO = new RequestDAO();
        requestDAO.removeAllRequests();
        System.out.println("Toutes les requêtes ont été supprimées.");
    }

    // 📚 Liste tous les livres dans le système
    public ArrayList<Book> viewBooks() throws SQLException {
        BookDAO bookDAO = new BookDAO();
        return bookDAO.getAllBooks();
    }

    // ➕ Ajoute un nouveau livre
    public void addBook(String title, String description, String author, int year, float price, boolean paymentRequired) throws SQLException {
        BookDAO bookDAO = new BookDAO();
        bookDAO.addBook(title, description, author, year, price, paymentRequired);
        System.out.println("Livre ajouté avec succès.");
    }

    // ✏️ Modifie les informations d’un livre
    public void editBook(int isbn, String[] fieldsToUpdate, String[] newValues) throws SQLException {
        BookDAO bookDAO = new BookDAO();

        for (String field : fieldsToUpdate) {
            switch (field) {
                case "1" -> bookDAO.updateTitle(isbn, newValues[0]);
                case "2" -> bookDAO.updateDescription(isbn, newValues[1]);
                case "3" -> bookDAO.updateAuthor(isbn, newValues[2]);
                case "4" -> bookDAO.updateYear(isbn, Integer.parseInt(newValues[3]));
                case "5" -> bookDAO.updatePrice(isbn, Float.parseFloat(newValues[4]));
                case "6" -> bookDAO.updatePaymentRequired(isbn, Boolean.parseBoolean(newValues[5]));
            }
        }

        System.out.println("Livre mis à jour.");
    }

    // 🗑️ Supprime un livre (et ses réservations si nécessaire)
    public void removeBook(int isbn) throws SQLException {
        BookDAO bookDAO = new BookDAO();
        ReservationDAO reservationDAO = new ReservationDAO();

        ArrayList<Reservation> reservations = reservationDAO.getReservationsByBook(isbn);

        if (reservations == null || reservations.isEmpty()) {
            bookDAO.removeBook(isbn);
            System.out.println("Livre supprimé.");
        } else {
            System.out.println("Réservations trouvées. Suppression des réservations avant suppression du livre...");
            for (Reservation reservation : reservations) {
                User user = reservation.getUser();
                user.getPaymentMethod().refund(reservation.getBook());  // appel à refund sur la stratégie
                reservationDAO.removeReservation(reservation.getCode());
            }
            bookDAO.removeBook(isbn);
            System.out.println("Livre supprimé avec ses réservations.");
        }
    }

    // 🔍 Recherche de livres par titre
    public ArrayList<Book> searchBooks(String title) throws SQLException {
        BookDAO bookDAO = new BookDAO();
        return bookDAO.getBooksByTitle(title);
    }
}
