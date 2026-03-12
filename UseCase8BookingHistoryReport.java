import java.util.*;

// Represents a hotel reservation
class Reservation {
    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    @Override
    public String toString() {
        return "Reservation[ID=" + reservationId + ", Guest=" + guestName + "]";
    }
}

// Maintains booking history
class BookingHistory {
    private List<Reservation> confirmedReservations = new ArrayList<>();

    // Add a confirmed reservation
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(confirmedReservations);
    }

    // Generate a simple report
    public void generateReport() {
        System.out.println("\n--- Booking History Report ---");
        System.out.println("Total Bookings: " + confirmedReservations.size());
        for (Reservation r : confirmedReservations) {
            System.out.println(r);
        }
        System.out.println("--- End of Report ---\n");
    }
}

// Main program to demonstrate Use Case 8
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("R001", "Alice");
        Reservation r2 = new Reservation("R002", "Bob");
        Reservation r3 = new Reservation("R003", "Charlie");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Display booking history
        System.out.println("All Confirmed Reservations:");
        for (Reservation r : history.getAllReservations()) {
            System.out.println(r);
        }

        // Generate summary report
        history.generateReport();
    }
}