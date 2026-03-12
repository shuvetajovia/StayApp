import java.util.*;

// Represents an optional add-on service
class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name + " ($" + cost + ")";
    }
}

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

// Manages add-on services for reservations
class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // Add a service to a reservation
    public void addService(String reservationId, AddOnService service) {
        reservationServices.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
    }

    // Get all services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, Collections.emptyList());
    }

    // Calculate total additional cost for a reservation
    public double calculateTotalCost(String reservationId) {
        return getServices(reservationId).stream().mapToDouble(AddOnService::getCost).sum();
    }
}

// Main program to demonstrate Use Case 7
public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample reservations
        Reservation res1 = new Reservation("R001", "Alice");
        Reservation res2 = new Reservation("R002", "Bob");

        // Sample services
        AddOnService breakfast = new AddOnService("Breakfast", 20.0);
        AddOnService spa = new AddOnService("Spa Access", 50.0);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 30.0);

        AddOnServiceManager manager = new AddOnServiceManager();

        // Simulate guest selecting services
        System.out.println("Select services for reservation R001 (Alice):");
        manager.addService(res1.getReservationId(), breakfast);
        manager.addService(res1.getReservationId(), spa);

        System.out.println("Select services for reservation R002 (Bob):");
        manager.addService(res2.getReservationId(), airportPickup);

        // Display reservation details with selected services
        List<Reservation> reservations = Arrays.asList(res1, res2);
        for (Reservation r : reservations) {
            System.out.println("\n" + r);
            List<AddOnService> services = manager.getServices(r.getReservationId());
            System.out.println("Selected Services: " + services);
            System.out.println("Total Additional Cost: $" + manager.calculateTotalCost(r.getReservationId()));
        }

        scanner.close();
    }
}