package main.java.ORM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookDAO {

    private Connection connection;

    public BookDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void addBook(String title, String author, String isbn) throws SQLException {
        String sql = String.format(
                "INSERT INTO \"Book\" (title, author, isbn) VALUES ('%s', '%s', '%s')",
                title, author, isbn
        );

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public void removeBook(String isbn) throws SQLException {
        String sql = String.format("DELETE FROM \"Book\" WHERE isbn = '%s'", isbn);

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Book removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public void updateBookTitle(String isbn, String newTitle) throws SQLException {
        String sql = String.format(
                "UPDATE \"Book\" SET title = '%s' WHERE isbn = '%s'",
                newTitle, isbn
        );

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Book title updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
}

    public Book getBook(int id) throws SQLException {
        Book book = null;
        String sql = "SELECT * FROM \"Book\" WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    book = new Book(
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
        return book;
    }

    public ArrayList<Book> getBooksByTitle(String title) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM \"Book\" WHERE LOWER(title) LIKE LOWER(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("publisher"),
                            rs.getInt("publicationYear"),
                            rs.getString("isbn"),
                            rs.getInt("quantityAvailable")
                    ));
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
                    books.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("publisher"),
                            rs.getInt("publicationYear"),
                            rs.getString("isbn"),
                            rs.getInt("quantityAvailable")
                    ));
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
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("publicationYear"),
                        rs.getString("isbn"),
                        rs.getInt("quantityAvailable")
                ));
            }
        }
        return books;
    }

    public void updateTitle(int id, String title) throws SQLException {
        String sql = "UPDATE \"Book\" SET title = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Titolo aggiornato con successo.");
        }
    }

    public void updateAuthor(int id, String author) throws SQLException {
        String sql = "UPDATE \"Book\" SET author = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, author);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Autore aggiornato con successo.");
        }
    }

    public void updatePublisher(int id, String publisher) throws SQLException {
        String sql = "UPDATE \"Book\" SET publisher = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, publisher);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Editore aggiornato con successo.");
        }
    }

    public void updatePublicationYear(int id, int year) throws SQLException {
        String sql = "UPDATE \"Book\" SET publicationYear = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, year);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Anno aggiornato con successo.");
        }
    }

    public void updateQuantityAvailable(int id, int quantity) throws SQLException {
        String sql = "UPDATE \"Book\" SET quantityAvailable = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Quantità disponibile aggiornata con successo.");
        }
    }

    public void updateISBN(int id, String isbn) throws SQLException {
        String sql = "UPDATE \"Book\" SET isbn = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("ISBN aggiornato con successo.");
        }
    }
}
