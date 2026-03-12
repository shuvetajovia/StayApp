import java.util.*;

// Represents a hotel reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation[ID=" + reservationId + ", Guest=" + guestName + ", RoomType=" + roomType + "]";
    }
}

// Manages inventory with thread-safe allocation
class Inventory {
    private Map<String, Integer> roomCounts = new HashMap<>();

    public Inventory() {
        roomCounts.put("Single", 5);
        roomCounts.put("Double", 3);
        roomCounts.put("Suite", 2);
    }

    // Synchronized allocation to prevent race conditions
    public synchronized boolean allocateRoom(String roomType) {
        int available = roomCounts.getOrDefault(roomType, 0);
        if (available > 0) {
            roomCounts.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void releaseRoom(String roomType) {
        roomCounts.put(roomType, roomCounts.getOrDefault(roomType, 0) + 1);
    }

    public synchronized void displayInventory() {
        System.out.println("Current Inventory: " + roomCounts);
    }
}

// Manages reservations in a thread-safe way
class BookingManager {
    private Map<String, Reservation> reservations = new HashMap<>();
    private Inventory inventory;

    public BookingManager(Inventory inventory) {
        this.inventory = inventory;
    }

    // Synchronized booking to avoid conflicts
    public synchronized boolean bookRoom(String reservationId, String guestName, String roomType) {
        if (reservations.containsKey(reservationId)) {
            System.out.println("Duplicate reservation ID: " + reservationId);
            return false;
        }

        if (inventory.allocateRoom(roomType)) {
            Reservation res = new Reservation(reservationId, guestName, roomType);
            reservations.put(reservationId, res);
            System.out.println("Booking confirmed: " + res);
            return true;
        } else {
            System.out.println("No available rooms for type: " + roomType + " for guest " + guestName);
            return false;
        }
    }

    public synchronized void displayAllReservations() {
        System.out.println("\nAll Reservations:");
        for (Reservation r : reservations.values()) {
            System.out.println(r);
        }
    }
}

// Simulates a guest booking in a separate thread
class GuestBooking implements Runnable {
    private BookingManager manager;
    private String reservationId;
    private String guestName;
    private String roomType;

    public GuestBooking(BookingManager manager, String reservationId, String guestName, String roomType) {
        this.manager = manager;
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public void run() {
        manager.bookRoom(reservationId, guestName, roomType);
    }
}

// Main program to demonstrate Use Case 11
public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {
        Inventory inventory = new Inventory();
        BookingManager manager = new BookingManager(inventory);

        // Simulate multiple guests booking concurrently
        Thread t1 = new Thread(new GuestBooking(manager, "R001", "Alice", "Single"));
        Thread t2 = new Thread(new GuestBooking(manager, "R002", "Bob", "Single"));
        Thread t3 = new Thread(new GuestBooking(manager, "R003", "Charlie", "Suite"));
        Thread t4 = new Thread(new GuestBooking(manager, "R004", "Diana", "Double"));
        Thread t5 = new Thread(new GuestBooking(manager, "R005", "Eve", "Single"));

        // Start all threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // Wait for all threads to finish
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

        // Display final reservation and inventory state
        manager.displayAllReservations();
        inventory.displayInventory();
    }
}