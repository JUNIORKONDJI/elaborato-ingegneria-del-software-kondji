-- init_sample_data_booktrack.sql

-- INSERT USERS

INSERT INTO "User" (name, surname, age, username, email, password, creditCard, payPal)
VALUES ('Anna', 'Bianchi', 27, 'anna.bianchi', 'anna.bianchi@example.com', 'annaSecure1', null, null);
INSERT INTO "CreditCard" (cardNumber, cardExpirationDate, cardSecurityCode)
VALUES ('100000001', '2026-11-30', '111');
UPDATE "User" SET creditCard = '100000001' WHERE username = 'anna.bianchi';

INSERT INTO "User" (name, surname, age, username, email, password, creditCard, payPal)
VALUES ('Marco', 'Rossi', 33, 'marco.rossi', 'marco.rossi@example.com', 'marcoSecure2', null, null);
INSERT INTO "PayPal" (accountEmail, accountPassword)
VALUES ('marco.rossi@example.com', 'marcoSecure2');
DO $$
DECLARE PayPalUniqueCode INTEGER;
BEGIN
SELECT uniqueCode INTO PayPalUniqueCode FROM "PayPal" WHERE accountEmail = 'marco.rossi@example.com';
UPDATE "User" SET payPal = PayPalUniqueCode WHERE username = 'marco.rossi';
END $$;

-- INSERT BOOKS (example books)
INSERT INTO "Book" (isbn, title, author, category, availableCopies)
VALUES ('9781234567890', 'Clean Code', 'Robert C. Martin', 'Software Engineering', 5);

INSERT INTO "Book" (isbn, title, author, category, availableCopies)
VALUES ('9781234567891', 'Effective Java', 'Joshua Bloch', 'Programming', 4);

-- RESERVATIONS
INSERT INTO "BookReservation" (user_id, book_isbn, reservationDate)
VALUES (1, '9781234567890', '2025-06-01');

-- BORROWINGS
INSERT INTO "BookBorrowing" (user_id, book_isbn, borrowDate, returnDate)
VALUES (2, '9781234567891', '2025-06-01', null);

-- SAMPLE REQUESTS
INSERT INTO "Request" (user_id, description)
VALUES (1, '| UPDATE | Book isbn: 9781234567890 | Change category: Computer Science |');

INSERT INTO "Request" (user_id, description)
VALUES (2, '| DELETE | Book isbn: 9781234567891 | Reason: Duplicate entry |');
