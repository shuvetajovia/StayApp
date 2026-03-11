import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Alice", "Deluxe"));
        bookingQueue.add(new Reservation("Bob", "Suite"));
        bookingQueue.add(new Reservation("Charlie", "Standard"));

        Map<String, Set<String>> allocatedRooms = new HashMap<>();

        int roomCounter = 101;

        while (!bookingQueue.isEmpty()) {

            Reservation r = bookingQueue.poll();

            String roomId = "R" + roomCounter++;
            allocatedRooms.putIfAbsent(r.roomType, new HashSet<>());
            allocatedRooms.get(r.roomType).add(roomId);

            System.out.println(
                    "Reservation confirmed for "
                            + r.guestName
                            + " | Room Type: "
                            + r.roomType
                            + " | Room ID: "
                            + roomId
            );
        }

        System.out.println("\nAllocated Rooms:");
        System.out.println(allocatedRooms);
    }
}
