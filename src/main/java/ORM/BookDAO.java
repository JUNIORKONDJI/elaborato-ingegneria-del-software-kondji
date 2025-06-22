package main.java.ORM;

import main.java.CoreEntities.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {

    private Connection connection;

    public BookDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error initializing BookDAO: " + e.getMessage());
        }
    }

    public void addBook(String title, String author, String isbn) throws SQLException {
        String sql = "INSERT INTO \"Book\" (title, author, isbn) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, isbn);
            ps.executeUpdate();
            System.out.println("Book added successfully.");
        }
    }

    public void removeBook(String isbn) throws SQLException {
        String sql = "DELETE FROM \"Book\" WHERE isbn = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ps.executeUpdate();
            System.out.println("Book removed successfully.");
        }
    }

    public void updateBookTitle(String isbn, String newTitle) throws SQLException {
        String sql = "UPDATE \"Book\" SET title = ? WHERE isbn = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newTitle);
            ps.setString(2, isbn);
            ps.executeUpdate();
            System.out.println("Book title updated successfully.");
        }
    }

    public Book getBook(int id) throws SQLException {
        String sql = "SELECT * FROM \"Book\" WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("publisher"),
                            rs.getInt("publicationYear"),
                            rs.getString("isbn"),
                            rs.getInt("quantityAvailable")
                    );
                }
            }
        }
        return null;
    }

    public ArrayList<Book> getBooksByTitle(String title) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM \"Book\" WHERE LOWER(title) LIKE LOWER(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        }
        return books;
    }

    public ArrayList<Book> getBooksByAuthor(String author) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM \"Book\" WHERE LOWER(author) LIKE LOWER(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + author + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        }
        return books;
    }

    public ArrayList<Book> getAllBooks() throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM \"Book\"";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        }
        return books;
    }

    public void updateTitle(int id, String title) throws SQLException {
        updateField("title", id, title);
    }

    public void updateAuthor(int id, String author) throws SQLException {
        updateField("author", id, author);
    }

    public void updatePublisher(int id, String publisher) throws SQLException {
        updateField("publisher", id, publisher);
    }

    public void updatePublicationYear(int id, int year) throws SQLException {
        updateField("publicationYear", id, year);
    }

    public void updateQuantityAvailable(int id, int quantity) throws SQLException {
        updateField("quantityAvailable", id, quantity);
    }

    public void updateISBN(int id, String isbn) throws SQLException {
        updateField("isbn", id, isbn);
    }

    public boolean existsByISBN(String isbn) throws SQLException {
        String sql = "SELECT 1 FROM \"Book\" WHERE isbn = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        return new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("publisher"),
                rs.getInt("publicationYear"),
                rs.getString("isbn"),
                rs.getInt("quantityAvailable")
        );
    }

    private void updateField(String column, int id, Object value) throws SQLException {
        String sql = "UPDATE \"Book\" SET " + column + " = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println(column + " updated successfully.");
        }
    }
}
