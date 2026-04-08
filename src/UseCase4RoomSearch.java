package com.bookmystay.uc4;

import java.util.*;

// ===== Abstract Room =====
abstract class Room {
    String type;
    int beds;
    double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public void display() {
        System.out.println("Room: " + type +
                " | Beds: " + beds +
                " | Price: ₹" + price);
    }
}

// ===== Concrete Rooms =====
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2000);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

// ===== Inventory (Read-Only Access) =====
class RoomInventory {

    private Map<String, Integer> data = new HashMap<>();

    public RoomInventory() {
        data.put("Single Room", 2);
        data.put("Double Room", 0); // unavailable
        data.put("Suite Room", 1);
    }

    // Only GET (no update → read-only)
    public int getAvailability(String type) {
        return data.getOrDefault(type, 0);
    }

    public Set<String> getRoomTypes() {
        return data.keySet();
    }
}

// ===== Search Service =====
class SearchService {

    public void searchRooms(RoomInventory inventory, List<Room> rooms) {

        System.out.println("---- Available Rooms ----\n");

        for (Room r : rooms) {

            int available = inventory.getAvailability(r.type);

            // show only available rooms
            if (available > 0) {
                r.display();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// ===== Main Class =====
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====\n");

        // Step 1: Create rooms
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Step 2: Inventory
        RoomInventory inventory = new RoomInventory();

        // Step 3: Search
        SearchService search = new SearchService();
        search.searchRooms(inventory, rooms);

        System.out.println("===== Done =====");
    }
}

