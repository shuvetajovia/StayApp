import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return guestName + " requested " + roomType + " room";
    }
}

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Guest booking requests
        bookingQueue.add(new Reservation("Alice", "Deluxe"));
        bookingQueue.add(new Reservation("Bob", "Suite"));
        bookingQueue.add(new Reservation("Charlie", "Standard"));

        System.out.println("Booking Requests in Queue:");

        for (Reservation r : bookingQueue) {
            System.out.println(r);
        }

        System.out.println("\nRequests waiting for allocation...");
    }
}
