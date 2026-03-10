

import java.util.HashMap;
import java.util.Map;

// Room domain model
abstract class Room {
    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price per night: $" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 80);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 120);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500, 250);
    }
}

// Centralized inventory
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// Search service (read-only access)
class RoomSearchService {

    private RoomInventory inventory;
    private Map<String, Room> rooms;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;

        rooms = new HashMap<>();
        rooms.put("Single Room", new SingleRoom());
        rooms.put("Double Room", new DoubleRoom());
        rooms.put("Suite Room", new SuiteRoom());
    }

    public void searchAvailableRooms() {

        System.out.println("\nAvailable Rooms:\n");

        for (String roomType : rooms.keySet()) {

            int available = inventory.getAvailability(roomType);

            if (available > 0) {
                Room room = rooms.get(roomType);
                room.displayRoomDetails();
                System.out.println("Available: " + available);
                System.out.println("--------------------------------");
            }
        }
    }
}

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Book My Stay - Hotel Booking System");
        System.out.println("Version: 4.1");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService(inventory);

        searchService.searchAvailableRooms();
    }
}