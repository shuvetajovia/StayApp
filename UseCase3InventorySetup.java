/**
 * UseCase3InventorySetup
 *
 * Demonstrates centralized room inventory
 * using a HashMap data structure.
 *
 * @author Student
 * @version 3.1
 */
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Room Inventory System v3.1");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nChecking availability of Single Room:");
        System.out.println("Available: " + inventory.getAvailability("Single Room"));

        System.out.println("\nUpdating availability of Suite Room...");
        inventory.updateAvailability("Suite Room", 5);

        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();
    }
}