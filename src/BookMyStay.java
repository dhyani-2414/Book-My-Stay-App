

import java.util.*;

// Reservation class representing a booking request
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Centralized room inventory
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean allocateRoom(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        } else {
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}

// Room allocation service
class RoomAllocationService {

    private RoomInventory inventory;
    private Queue<Reservation> requestQueue;
    private Map<String, Set<String>> allocatedRooms;

    public RoomAllocationService(RoomInventory inventory, Queue<Reservation> requestQueue) {
        this.inventory = inventory;
        this.requestQueue = requestQueue;
        allocatedRooms = new HashMap<>();
    }

    // Generate unique room ID
    private String generateRoomID(String roomType) {
        Set<String> assigned = allocatedRooms.getOrDefault(roomType, new HashSet<>());
        String roomID;
        do {
            roomID = roomType.substring(0, 2).toUpperCase() + "-" + (new Random().nextInt(100) + 1);
        } while (assigned.contains(roomID));
        assigned.add(roomID);
        allocatedRooms.put(roomType, assigned);
        return roomID;
    }

    // Process all booking requests
    public void processBookings() {
        System.out.println("\nProcessing Booking Requests:");

        while (!requestQueue.isEmpty()) {
            Reservation res = requestQueue.poll();
            String roomType = res.getRoomType();

            if (inventory.allocateRoom(roomType)) {
                String roomID = generateRoomID(roomType);
                System.out.println("Booking confirmed for " + res.getGuestName() +
                        " | Room Type: " + roomType + " | Room ID: " + roomID);
            } else {
                System.out.println("Sorry " + res.getGuestName() +
                        ", no " + roomType + " available at the moment.");
            }
        }
    }
}

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Book My Stay - Hotel Booking System");
        System.out.println("Version: 6.1");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.offer(new Reservation("Alice", "Single Room"));
        bookingQueue.offer(new Reservation("Bob", "Double Room"));
        bookingQueue.offer(new Reservation("Charlie", "Suite Room"));
        bookingQueue.offer(new Reservation("Diana", "Single Room"));
        bookingQueue.offer(new Reservation("Eve", "Suite Room"));
        bookingQueue.offer(new Reservation("Frank", "Suite Room")); // Should fail due to limited availability

        RoomAllocationService allocationService = new RoomAllocationService(inventory, bookingQueue);

        allocationService.processBookings();

        inventory.displayInventory();
    }
}