package coreentities.librarymanagement;

public class User {

    // === Attributs ===
    private int id;
    private String name;
    private String surname;
    private int age;
    private String username;
    private String email;
    private String password;
    private PaymentStrategy paymentMethod;

    // === Constructeurs ===

    public User(int id, String name, String surname, int age, String username, String email, String password, PaymentStrategy paymentMethod) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.username = username;
        this.email = email;
        this.password = password;
        this.paymentMethod = paymentMethod;
    }

    public User(String name, String surname, int age, String username, String email, String password, PaymentStrategy paymentMethod) {
        this(-1, name, surname, age, username, email, password, paymentMethod);
    }

    public User(int id, String name, String surname, int age, String username, String email, String password) {
        this(id, name, surname, age, username, email, password, null);
    }

    public User(String name, String surname, int age, String username, String email, String password) {
        this(-1, name, surname, age, username, email, password, null);
    }

    // === Getters ===

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public int getAge() { return age; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public PaymentStrategy getPaymentMethod() { return paymentMethod; }

    public String getPersonalData() {
        return "Name: " + name + "\nSurname: " + surname + "\nAge: " + age;
    }

    public String getLoginData() {
        return "Username: " + username + "\nEmail: " + email + "\nPassword: " + password;
    }

    public String getPaymentMethodType() {
        return paymentMethod != null ? paymentMethod.getPaymentMethod() : "None";
    }

    public String getPaymentData() {
        return paymentMethod != null ? paymentMethod.getPaymentData() : "No payment method set";
    }

    // === Setters ===

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setAge(int age) { this.age = age; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPaymentMethod(PaymentStrategy paymentMethod) { this.paymentMethod = paymentMethod; }

    // === Méthodes de gestion des moyens de paiement ===

    public void setCreditCard(String cardNumber, String expirationDate, String securityCode) {
        this.paymentMethod = new CreditCard(this.name, this.surname, cardNumber, expirationDate, securityCode);
    }

    public void setPayPal(String uniqueCode, String accountEmail, String accountPassword) {
        this.paymentMethod = new PayPal(this.name, this.surname, uniqueCode, accountEmail, accountPassword);
    }

    public String[] getPaymentCodes() {
        if (paymentMethod instanceof CreditCard) {
            return new String[]{((CreditCard) paymentMethod).getCardNumber(), "null"};
        } else if (paymentMethod instanceof PayPal) {
            return new String[]{"null", String.valueOf(((PayPal) paymentMethod).getUniqueCode())};
        }
        return new String[]{"null", "null"};
    }
}

