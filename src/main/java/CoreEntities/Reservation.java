package coreentities.librarymanagement;

public class Reservation {

    // Reservation data
    private User user;
    private Book book;
    private String paymentMethod;

    // Constructors
    public Reservation() {}

    public Reservation(User user, Book book, String paymentMethod) {
        this.user = user;
        this.book = book;
        this.paymentMethod = paymentMethod;
    }

    // Getters
    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Method to get reservation info
    public String getReservationData() {
        return "User: " + user.getUsername() +
                "\nBook: " + book.getTitle() +
                "\nPayment method: " + paymentMethod;
    }
}
