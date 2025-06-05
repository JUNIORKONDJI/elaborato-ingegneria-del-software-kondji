package coreentities.librarymanagement;

public class Book {

    // book data
    private int isbn;
    private String title, description, author;
    private int year;
    private float price;
    private boolean paymentRequired;

    // constructors
    public Book(String title, String description, String author, int year, float price, boolean paymentRequired) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.year = year;
        this.price = price;
        this.paymentRequired = paymentRequired;
    }

    public Book(String title, String description, String author, int year, float price) {
        this(title, description, author, year, price, false);
    }

    // getters
    public int getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public float getPrice() { return price; }
    public boolean isPaymentRequired() { return paymentRequired; }

    public String getBookData() {
        return "Title: " + title + "\nDescription: " + description + "\nAuthor: " + author + "\nYear: " + year +
                "\nPrice: " + price + "€\nPayment Required: " + (paymentRequired ? "Yes" : "No");
    }

    // setters
    public void setIsbn(int isbn) { this.isbn = isbn; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(int year) { this.year = year; }
    public void setPrice(float price) { this.price = price; }
    public void setPaymentRequired(boolean paymentRequired) { this.paymentRequired = paymentRequired; }
}
