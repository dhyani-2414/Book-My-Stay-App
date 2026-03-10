

import java.util.HashMap;
import java.util.Map;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability of a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability of a room type
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display current inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}

public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Book My Stay - Hotel Booking System");
        System.out.println("Version: 3.1");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nChecking availability for Double Room:");
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        System.out.println("\nUpdating availability for Double Room...");
        inventory.updateAvailability("Double Room", 4);

        inventory.displayInventory();
    }
}