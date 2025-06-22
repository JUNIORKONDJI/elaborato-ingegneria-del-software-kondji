package coreentities.librarymanagement;

import java.util.Scanner;
import main.java.CoreEntities.Book;
import main.java.CoreEntities.PaymentStrategy;

public class PayPal implements PaymentStrategy {

    // Costanti
    private static final float REFUND_PERCENTAGE = 1f;
    private static final float COMMISSION_PERCENTAGE = 0.05f;

    // Dati del proprietario
    private String ownerName, ownerSurname;

    // Dati account
    private int uniqueCode;
    private String accountEmail, accountPassword;

    // Costruttore
    public PayPal(String ownerName, String ownerSurname, String uniqueCode, String accountEmail, String accountPassword) {
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.uniqueCode = Integer.parseInt(uniqueCode);
        this.accountEmail = accountEmail;
        this.accountPassword = accountPassword;
    }

    // Getter - costanti
    public static float getRefundPercentage() { return REFUND_PERCENTAGE; }
    public static float getCommissionPercentage() { return COMMISSION_PERCENTAGE; }

    // Getter - dati utente
    public String getOwnerName() { return ownerName; }
    public String getOwnerSurname() { return ownerSurname; }

    // Getter - dati account
    public int getUniqueCode() { return uniqueCode; }
    public String getAccountEmail() { return accountEmail; }
    public String getAccountPassword() { return accountPassword; }

    public String getPayPalData() {
        return "Owner: " + ownerName + " " + ownerSurname +
                "\nUnique Code: " + uniqueCode +
                "\nAccount Email: " + accountEmail +
                "\nAccount Password: " + accountPassword;
    }

    @Override
    public void pay(Book book) {

        float amount = book.getPrice();

        if (amount < 0) throw new IllegalArgumentException("Invalid amount.");
        if (amount == 0) return;

        amount += amount * COMMISSION_PERCENTAGE;

        System.out.println("\nPaying for book: " + book.getTitle());
        System.out.println("Amount: " + amount + "€ (including " + (COMMISSION_PERCENTAGE * 100) + "% commission)");

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nConfirm payment? (yes/no)");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (!confirmation.equals("yes")) {
            System.out.println("Payment cancelled.");
            return;
        }

        System.out.println("Payment successful using PayPal!");
    }
}
