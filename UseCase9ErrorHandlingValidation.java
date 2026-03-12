import java.util.*;

// Custom exception for invalid bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Represents a hotel reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) throws InvalidBookingException {
        if (reservationId == null || reservationId.isEmpty()) {
            throw new InvalidBookingException("Reservation ID cannot be empty.");
        }
        if (guestName == null || guestName.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }
        if (!roomType.equalsIgnoreCase("Single") &&
                !roomType.equalsIgnoreCase("Double") &&
                !roomType.equalsIgnoreCase("Suite")) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation[ID=" + reservationId + ", Guest=" + guestName + ", Room=" + roomType + "]";
    }
}

// Manages reservations
class ReservationManager {
    private Map<String, Reservation> reservations = new HashMap<>();

    // Add a reservation after validation
    public void addReservation(Reservation reservation) throws InvalidBookingException {
        if (reservations.containsKey(reservation.getReservationId())) {
            throw new InvalidBookingException("Reservation ID already exists: " + reservation.getReservationId());
        }
        reservations.put(reservation.getReservationId(), reservation);
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    public Collection<Reservation> getAllReservations() {
        return reservations.values();
    }
}

// Main program to demonstrate Use Case 9
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        ReservationManager manager = new ReservationManager();

        try {
            // Valid booking
            Reservation r1 = new Reservation("R001", "Alice", "Single");
            manager.addReservation(r1);

            // Invalid booking: empty guest name
            Reservation r2 = new Reservation("R002", "", "Double");
            manager.addReservation(r2);

        } catch (InvalidBookingException e) {
            System.out.println("Booking Error: " + e.getMessage());
        }

        try {
            // Invalid booking: wrong room type
            Reservation r3 = new Reservation("R003", "Bob", "Penthouse");
            manager.addReservation(r3);
        } catch (InvalidBookingException e) {
            System.out.println("Booking Error: " + e.getMessage());
        }

        try {
            // Duplicate reservation ID
            Reservation r4 = new Reservation("R001", "Charlie", "Suite");
            manager.addReservation(r4);
        } catch (InvalidBookingException e) {
            System.out.println("Booking Error: " + e.getMessage());
        }

        // Display all valid reservations
        System.out.println("\nAll Valid Reservations:");
        for (Reservation r : manager.getAllReservations()) {
            System.out.println(r);
        }
    }
}
