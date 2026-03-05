import java.util.HashMap;
import java.util.Map;

/**
 * RoomInventory
 *
 * Centralized inventory manager that stores
 * available room counts using a HashMap.
 *
 * @author Student
 * @version 3.0
 */
public class RoomInventory {

    private Map<String, Integer> inventory;

    /**
     * Constructor initializes room availability
     */
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 7);
        inventory.put("Suite Room", 3);
    }

    /**
     * Get availability of a room type
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Update room availability
     */
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /**
     * Display entire inventory
     */
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}