package coreentities.librarymanagement;

public class ReservationMapper {

    public Reservation map(User user, Book book, String paymentMethod) {

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setPaymentMethod(paymentMethod);

        return reservation;

    }

}
