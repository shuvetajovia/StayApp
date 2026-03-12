import java.io.*;
import java.util.*;

// Represents a hotel reservation (Serializable for persistence)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
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

// Manages inventory (Serializable)
class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
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

    @Override
    public String toString() {
        return roomCounts.toString();
    }
}

// Manages bookings and persistence
class BookingManager {
    private Map<String, Reservation> reservations = new HashMap<>();
    private Inventory inventory;
    private static final String FILE_NAME = "booking_data.ser";

    public BookingManager(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean bookRoom(String reservationId, String guestName, String roomType) {
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
            System.out.println("No available rooms for type: " + roomType);
            return false;
        }
    }

    public void displayAllReservations() {
        System.out.println("\nAll Reservations:");
        for (Reservation r : reservations.values()) {
            System.out.println(r);
        }
    }

    // Save current state to file
    public void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(reservations);
            oos.writeObject(inventory);
            System.out.println("\nSystem state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Restore state from file
    @SuppressWarnings("unchecked")
    public void restoreState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No previous state found. Starting fresh.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            reservations = (Map<String, Reservation>) ois.readObject();
            inventory = (Inventory) ois.readObject();
            System.out.println("\nSystem state restored successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error restoring system state: " + e.getMessage());
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}

// Main program to demonstrate Use Case 12
public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        BookingManager manager = new BookingManager(inventory);

        // Restore previous state if available
        manager.restoreState();

        // Simulate bookings
        manager.bookRoom("R001", "Alice", "Single");
        manager.bookRoom("R002", "Bob", "Double");

        // Display current state
        manager.displayAllReservations();
        System.out.println("\nCurrent Inventory: " + manager.getInventory());

        // Save system state
        manager.saveState();
    }
}