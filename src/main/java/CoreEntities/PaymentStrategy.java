package coreentities.librarymanagement;

import java.util.Scanner;

public class CreditCard implements PaymentStrategy {

    // Constants
    private static final float REFUND_PERCENTAGE = 0.9f;
    private static final float COMMISSION_PERCENTAGE = 0.01f;

    // Owner data
    private String ownerName;
    private String ownerSurname;

    // Card data
    private String cardNumber;
    private String cardExpirationDate;
    private String cardSecurityCode;

    // Constructor
    public CreditCard(String ownerName, String ownerSurname, String cardNumber, String cardExpirationDate, String cardSecurityCode) {
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.cardNumber = cardNumber;
        this.cardExpirationDate = cardExpirationDate;
        this.cardSecurityCode = cardSecurityCode;
    }

    // Getters - constants
    public static float getRefundPercentage() {
        return REFUND_PERCENTAGE;
    }

    public static float getCommissionPercentage() {
        return COMMISSION_PERCENTAGE;
    }

    // Getters - owner
    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    // Getters - card
    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public String getCardSecurityCode() {
        return cardSecurityCode;
    }

    public String getCreditCardData() {
        return "Owner: " + ownerName + " " + ownerSurname +
                "\nCard Number: " + cardNumber +
                "\nExpiration Date: " + cardExpirationDate +
                "\nSecurity Code: " + cardSecurityCode;
    }

    @Override
    public void pay(Book book) {
        float amount = book.getPrice();

        if (amount < 0) throw new IllegalArgumentException("\nInvalid amount");
        if (amount == 0) return;

        amount += amount * COMMISSION_PERCENTAGE;

        System.out.println("\nPaying for book: " + book.getTitle());
        System.out.println("Amount: " + amount + "€ (including " + (COMMISSION_PERCENTAGE * 100) + "% commission)");

        System.out.println("\nConfirm the payment? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String confirmation = scanner.nextLine().toLowerCase();

        if (!confirmation.equals("yes")) {
            System.out.println("Payment cancelled.");
            return;
        }

        System.out.println("Paying with credit card...");
        simulateProcessing();

        System.out.println("Enter your credit card security code:");
        String inputCode;
        do {
            inputCode = scanner.nextLine();
            if (!inputCode.equals(cardSecurityCode)) {
                System.out.println("Incorrect code, try again:");
            }
        } while (!inputCode.equals(cardSecurityCode));

        System.out.println("Connecting to the bank...");
        simulateProcessing();

        System.out.println("Payment successful!");
    }

    @Override
    public void refund(Book book) {
        float amount = book.getPrice() * REFUND_PERCENTAGE;

        System.out.println("\nProcessing refund...");
        System.out.println("Book: " + book.getTitle() + ", User: " + ownerName + " " + ownerSurname);
        System.out.println("Amount refundable: " + amount + "€ (after " + Math.round((1 - REFUND_PERCENTAGE) * 1000) / 10.0 + "% commission)");

        simulateProcessing();
        System.out.println("Connecting to the bank...");
        simulateProcessing();

        System.out.println("Refund successful!");
    }

    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }

    @Override
    public String getPaymentData() {
        return getCreditCardData();
    }

    // Helper method
    private void simulateProcessing() {
        try {
            Thread.sleep(1000); // simulate processing time
        } catch (InterruptedException e) {
            System.err.println("Error during processing: " + e.getMessage());
        }
    }
}
