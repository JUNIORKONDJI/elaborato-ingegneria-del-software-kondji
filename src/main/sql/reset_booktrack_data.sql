-- reset_booktrack.sql - Reset completo del database BookTrack

DROP TABLE IF EXISTS "BookBorrowing" CASCADE;
DROP TABLE IF EXISTS "BookReservation" CASCADE;
DROP TABLE IF EXISTS "Book" CASCADE;
DROP TABLE IF EXISTS "CreditCard" CASCADE;
DROP TABLE IF EXISTS "PayPal" CASCADE;
DROP TABLE IF EXISTS "User" CASCADE;

-- Carta di Credito
CREATE TABLE "CreditCard" (
                              cardNumber VARCHAR(50) PRIMARY KEY,
                              cardType VARCHAR(50),
                              cardExpirationDate VARCHAR(50) NOT NULL,
                              cardSecurityCode VARCHAR(50) NOT NULL
);

-- Conto PayPal
CREATE TABLE "PayPal" (
                          uniqueCode SERIAL PRIMARY KEY,
                          accountEmail VARCHAR(100) NOT NULL UNIQUE,
                          accountPassword VARCHAR(100) NOT NULL
);

-- Utente
CREATE TABLE "User" (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(50),
                        surname VARCHAR(50),
                        age INTEGER CONSTRAINT bt_age_positive CHECK (age >= 0 AND age < 150),
                        username VARCHAR(50) NOT NULL UNIQUE,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        password VARCHAR(100) NOT NULL,
                        creditCard VARCHAR(50) UNIQUE,
                        payPal INTEGER UNIQUE,
                        FOREIGN KEY (creditCard) REFERENCES "CreditCard"(cardNumber),
                        FOREIGN KEY (payPal) REFERENCES "PayPal"(uniqueCode),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Libro
CREATE TABLE "Book" (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(150) NOT NULL,
                        author VARCHAR(100) NOT NULL,
                        isbn VARCHAR(20) UNIQUE NOT NULL,
                        available BOOLEAN DEFAULT TRUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Prenotazione libro
CREATE TABLE "BookReservation" (
                                   user_id INTEGER NOT NULL,
                                   book_id INTEGER NOT NULL,
                                   reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   PRIMARY KEY (user_id, book_id),
                                   FOREIGN KEY (user_id) REFERENCES "User"(id),
                                   FOREIGN KEY (book_id) REFERENCES "Book"(id)
);

-- Prestito libro
CREATE TABLE "BookBorrowing" (
                                 user_id INTEGER NOT NULL,
                                 book_id INTEGER NOT NULL,
                                 borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 due_date TIMESTAMP,
                                 PRIMARY KEY (user_id, book_id),
                                 FOREIGN KEY (user_id) REFERENCES "User"(id),
                                 FOREIGN KEY (book_id) REFERENCES "Book"(id)
);

-- Inserimento utente amministratore
INSERT INTO "User" (id, username, email, password) VALUES (0, 'ADMIN', '', 'admin');
