-- CREDIT CARD
CREATE TABLE IF NOT EXISTS "CreditCard" (
                                            cardNumber VARCHAR(50) PRIMARY KEY,
                                            cardType VARCHAR(50),
                                            cardExpirationDate VARCHAR(10),
                                            cardSecurityCode VARCHAR(10)
);

-- PAYPAL
CREATE TABLE IF NOT EXISTS "PayPal" (
                                        uniqueCode SERIAL PRIMARY KEY,
                                        accountEmail VARCHAR(100) UNIQUE NOT NULL,
                                        accountPassword VARCHAR(100) NOT NULL
);

-- USERS
CREATE TABLE IF NOT EXISTS "User" (
                                      id SERIAL PRIMARY KEY,
                                      name VARCHAR(50),
                                      surname VARCHAR(50),
                                      age INTEGER CHECK (age >= 0 AND age < 150),
                                      username VARCHAR(50) NOT NULL UNIQUE,
                                      email VARCHAR(100) NOT NULL UNIQUE,
                                      password VARCHAR(100) NOT NULL,
                                      creditCard VARCHAR(50) UNIQUE,
                                      payPal INTEGER UNIQUE,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      FOREIGN KEY (creditCard) REFERENCES "CreditCard"(cardNumber),
                                      FOREIGN KEY (payPal) REFERENCES "PayPal"(uniqueCode)
);

-- BOOKS
CREATE TABLE IF NOT EXISTS "Book" (
                                      isbn VARCHAR(20) PRIMARY KEY,
                                      title VARCHAR(200) NOT NULL,
                                      author VARCHAR(100),
                                      publisher VARCHAR(100),
                                      genre VARCHAR(50),
                                      releaseYear INTEGER,
                                      availableCopies INTEGER DEFAULT 0 CHECK (availableCopies >= 0)
);

-- RESERVATION
CREATE TABLE IF NOT EXISTS "Reservation" (
                                             id SERIAL PRIMARY KEY,
                                             user_id INTEGER NOT NULL,
                                             book_isbn VARCHAR(20) NOT NULL,
                                             reservationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                             status VARCHAR(30) DEFAULT 'pending',
                                             FOREIGN KEY (user_id) REFERENCES "User"(id),
                                             FOREIGN KEY (book_isbn) REFERENCES "Book"(isbn)
);

-- BORROWING
CREATE TABLE IF NOT EXISTS "Borrowing" (
                                           id SERIAL PRIMARY KEY,
                                           user_id INTEGER NOT NULL,
                                           book_isbn VARCHAR(20) NOT NULL,
                                           borrowingDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           returnDate TIMESTAMP,
                                           returned BOOLEAN DEFAULT FALSE,
                                           FOREIGN KEY (user_id) REFERENCES "User"(id),
                                           FOREIGN KEY (book_isbn) REFERENCES "Book"(isbn)
);

-- REQUEST
CREATE TABLE IF NOT EXISTS "Request" (
                                         code SERIAL PRIMARY KEY,
                                         user_id INTEGER NOT NULL,
                                         description VARCHAR(1000) NOT NULL,
                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         FOREIGN KEY (user_id) REFERENCES "User"(id)
);

-- ADMIN (opzionale - se hai operazioni speciali o un superutente)
CREATE TABLE IF NOT EXISTS "Admin" (
                                       username VARCHAR(50) PRIMARY KEY,
                                       password VARCHAR(100) NOT NULL
);
