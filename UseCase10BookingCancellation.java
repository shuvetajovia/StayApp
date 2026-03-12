import java.util.*;

// Represents a hotel reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "Reservation[ID=" + reservationId + ", Guest=" + guestName + ", Room=" + roomId + "]";
    }
}

// Manages hotel inventory
class Inventory {
    private Map<String, Integer> roomCounts = new HashMap<>();

    public Inventory() {
        roomCounts.put("Single", 5);
        roomCounts.put("Double", 3);
        roomCounts.put("Suite", 2);
    }

    public boolean allocateRoom(String roomType) {
        int available = roomCounts.getOrDefault(roomType, 0);
        if (available > 0) {
            roomCounts.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void releaseRoom(String roomType) {
        roomCounts.put(roomType, roomCounts.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + roomCounts);
    }
}

// Manages reservations and cancellations
class BookingManager {
    private Map<String, Reservation> reservations = new HashMap<>();
    private Stack<String> releasedRooms = new Stack<>();
    private Inventory inventory;

    public BookingManager(Inventory inventory) {
        this.inventory = inventory;
    }

    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
        System.out.println("Booking confirmed: " + reservation);
    }

    public void cancelReservation(String reservationId, String roomType) {
        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation Error: Reservation not found: " + reservationId);
            return;
        }

        Reservation res = reservations.remove(reservationId);
        releasedRooms.push(res.getRoomId()); // Track released room
        inventory.releaseRoom(roomType);     // Restore inventory
        System.out.println("Booking cancelled: " + res);
    }

    public void displayAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No active reservations.");
            return;
        }
        System.out.println("Active Reservations:");
        for (Reservation r : reservations.values()) {
            System.out.println(r);
        }
    }
}

// Main program to demonstrate Use Case 10
public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        BookingManager manager = new BookingManager(inventory);

        // Simulate bookings
        Reservation r1 = new Reservation("R001", "Alice", "Single-101");
        Reservation r2 = new Reservation("R002", "Bob", "Double-201");
        Reservation r3 = new Reservation("R003", "Charlie", "Suite-301");

        manager.addReservation(r1);
        manager.addReservation(r2);
        manager.addReservation(r3);

        inventory.displayInventory();

        // Cancel reservations
        System.out.println("\nCancelling reservation R002 (Bob):");
        manager.cancelReservation("R002", "Double");

        System.out.println("\nCancelling non-existent reservation R004:");
        manager.cancelReservation("R004", "Single");

        System.out.println("\nCurrent Reservations After Cancellation:");
        manager.displayAllReservations();

        System.out.println("\nInventory After Cancellation:");
        inventory.displayInventory();
    }
}