// VERSIONE CONVERTITA PER BOOKTRACK CON COMMENTI IN ITALIANO
// Sostituisce eventi con libri e partecipazioni con prenotazioni
// La struttura logica rimane identica, ma adattata al contesto gestionale della biblioteca

package main;

import main.java.LibraryManagement.*;
import main.java.CoreEntities.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        gestisciAccesso();
    }

    // Gestione accesso iniziale (login, registrazione, accesso admin)
    public static void gestisciAccesso() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        LoginController loginController = new LoginController();

        String input;
        do {
            System.out.println("""
                BENVENUTO IN BOOKTRACK
                1. Accedi
                2. Registrati
                3. Area Admin
                4. Esci
            """);

            input = scanner.nextLine();
            switch (input) {
                case "1" -> {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    User user = loginController.login(username, password);
                    if (user != null) gestisciUtente(user);
                }
                case "2" -> {
                    String[] dati = registraUtente();
                    User user = loginController.register(
                            dati[0], dati[1], Integer.parseInt(dati[2]),
                            dati[3], dati[4], dati[5], dati[6], dati[7], dati[8], dati[9]);
                    if (user != null) gestisciUtente(user);
                }
                case "3" -> {
                    System.out.print("Password Admin: ");
                    String password = scanner.nextLine();
                    if (loginController.adminLogin(password) != null)
                        gestisciAdmin();
                }
                case "4" -> System.exit(0);
                default -> System.out.println("Opzione non valida. Riprova.");
            }
        } while (true);
    }

    // Gestione azioni utente loggato
    public static void gestisciUtente(User user) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("""
                MENU UTENTE
                1. Visualizza e prenota libri
                2. Gestisci i tuoi libri (come autore)
                3. Profilo personale
                4. Logout
            """);

            input = scanner.nextLine();
            switch (input) {
                case "1" -> visualizzaPaginaLibri(user);
                case "2" -> gestisciPrenotazioniUtente(user);
                case "3" -> gestisciProfiloUtente(user);
                case "4" -> { return; }
                default -> System.out.println("Opzione non valida. Riprova.");
            }
        } while (true);
    }

    // Mostra tutti i libri disponibili e permette di prenotarne
    public static void visualizzaPaginaLibri(User user) throws SQLException {
        UserBookPageController bookController = new UserBookPageController(user);
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.println("""
                PAGINA LIBRI
                1. Visualizza tutti i libri
                2. Cerca un libro
                3. Prenota un libro
                4. Visualizza le tue prenotazioni
                5. Cancella una prenotazione
                6. Indietro
            """);

            input = scanner.nextLine();
            switch (input) {
                case "1" -> {
                    ArrayList<Book> books = bookController.viewBooks();
                    books.forEach(b -> System.out.println(b.getTitle() + " di " + b.getAuthor()));
                }
                case "2" -> {
                    System.out.print("Ricerca per (isbn, titolo, autore, anno): ");
                    String criterio = scanner.nextLine();
                    System.out.print("Inserisci valore: ");
                    String valore = scanner.nextLine();
                    ArrayList<Book> found = bookController.searchBook(criterio, valore);
                    found.forEach(b -> System.out.println(b.getTitle() + " - " + b.getAuthor()));
                }
                case "3" -> {
                    System.out.print("Inserisci ISBN del libro: ");
                    int id = scanner.nextInt(); scanner.nextLine();
                    bookController.reserveBook(id);
                }
                case "4" -> {
                    ArrayList<Book> books = bookController.viewReservedBooks();
                    books.forEach(b -> System.out.println(b.getTitle()));
                }
                case "5" -> {
                    System.out.print("Inserisci ISBN da cancellare: ");
                    int id = scanner.nextInt(); scanner.nextLine();
                    bookController.cancelReservation(id);
                }
                case "6" -> { return; }
                default -> System.out.println("Opzione non valida. Riprova.");
            }
        } while (true);
    }

    // Registrazione utente, restituisce i dati come array
    public static String[] registraUtente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Registrazione utente:");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Età: ");
        int eta = Integer.parseInt(scanner.nextLine());

        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Metodo di pagamento (CreditCard/PayPal): ");
        String metodo = scanner.nextLine();
        String dato1 = null, dato2 = null, dato3 = null;
        if (metodo.equalsIgnoreCase("CreditCard")) {
            System.out.print("Numero carta: "); dato1 = scanner.nextLine();
            System.out.print("Data scadenza: "); dato2 = scanner.nextLine();
            System.out.print("CVV: "); dato3 = scanner.nextLine();
        } else if (metodo.equalsIgnoreCase("PayPal")) {
            System.out.print("Email PayPal: "); dato2 = scanner.nextLine();
            System.out.print("Password PayPal: "); dato3 = scanner.nextLine();
        }

        return new String[]{nome, cognome, String.valueOf(eta), username, email, password, metodo, dato1, dato2, dato3};
    }

    // Placeholder per le altre funzioni (gestione prenotazioni, profilo, admin)
    public static void gestisciPrenotazioniUtente(User user) {
        System.out.println("[TODO] Gestione dei propri libri inseriti");
    }

    public static void gestisciProfiloUtente(User user) {
        System.out.println("[TODO] Modifica profilo, metodo di pagamento, dati personali");
    }

    public static void gestisciAdmin() {
        System.out.println("[TODO] Area amministratore - gestione utenti e libri");
    }
}
