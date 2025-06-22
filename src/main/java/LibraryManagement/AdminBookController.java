package main.java.LibraryManagement;

import main.java.ORM.BookDAO;
import main.java.CoreEntities.Book;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminBookController {

    private final BookDAO bookDAO;

    public AdminBookController() {
        this.bookDAO = new BookDAO();
    }

    // 📚 Liste tous les livres
    public ArrayList<Book> viewBooks() throws SQLException {
        return bookDAO.getAllBooks();
    }

    // ➕ Ajoute un livre
    public void addBook(String title, String author, String publisher, int year, String isbn, int quantity) throws SQLException {
        Book book = new Book(title, author, publisher, year, isbn, quantity);
        bookDAO.addBook(book);
        System.out.println("Livre ajouté avec succès.");
    }

    // ✏️ Modifie un livre
    public void editBook(int id, String[] updates) throws SQLException {
        if (updates[0] != null) bookDAO.updateTitle(id, updates[0]);
        if (updates[1] != null) bookDAO.updateAuthor(id, updates[1]);
        if (updates[2] != null) bookDAO.updatePublisher(id, updates[2]);
        if (updates[3] != null) bookDAO.updatePublicationYear(id, Integer.parseInt(updates[3]));
        if (updates[4] != null) bookDAO.updateISBN(id, updates[4]);
        if (updates[5] != null) bookDAO.updateQuantityAvailable(id, Integer.parseInt(updates[5]));
    }

    // 🗑️ Supprime un livre
    public void removeBook(int id) throws SQLException {
        bookDAO.removeBook(id);
        System.out.println("Livre supprimé avec succès.");
    }

    // 🔍 Recherche par mot-clé (titre ou auteur)
    public ArrayList<Book> searchBooks(String keyword) throws SQLException {
        ArrayList<Book> byTitle = bookDAO.getBooksByTitle(keyword);
        ArrayList<Book> byAuthor = bookDAO.getBooksByAuthor(keyword);

        for (Book book : byAuthor) {
            if (!byTitle.contains(book)) {
                byTitle.add(book);
            }
        }
        return byTitle;
    }
}
