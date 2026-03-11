import java.util.*;

// Room Domain Model
class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }
}

// Inventory (State Holder)
class Inventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoomType(String type, int count) {
        availability.put(type, count);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    public Set<String> getRoomTypes() {
        return availability.keySet();
    }
}

// Search Service (Read-Only)
class SearchService {

    public static void searchRooms(Inventory inventory, Map<String, Room> rooms) {
        System.out.println("Available Rooms:\n");

        for (String type : inventory.getRoomTypes()) {
            int available = inventory.getAvailability(type);

            // Validation: only show rooms with availability > 0
            if (available > 0) {
                Room room = rooms.get(type);

                System.out.println("Room Type: " + room.getType());
                System.out.println("Price: $" + room.getPrice());
                System.out.println("Amenities: " + room.getAmenities());
                System.out.println("Available Rooms: " + available);
                System.out.println("----------------------------");
            }
        }
    }
}

// Main Program
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        // Create Room Objects (Domain Model)
        Map<String, Room> roomData = new HashMap<>();
        roomData.put("Single", new Room("Single", 80, "WiFi, TV"));
        roomData.put("Double", new Room("Double", 120, "WiFi, TV, AC"));
        roomData.put("Suite", new Room("Suite", 200, "WiFi, TV, AC, Mini Bar"));

        // Inventory Setup
        Inventory inventory = new Inventory();
        inventory.addRoomType("Single", 5);
        inventory.addRoomType("Double", 0);   // Not available
        inventory.addRoomType("Suite", 2);

        // Guest performs search
        SearchService.searchRooms(inventory, roomData);
    }
}