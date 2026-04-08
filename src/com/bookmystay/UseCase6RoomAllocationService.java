import java.util.*;

// ===== Reservation Class =====
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// ===== Booking Queue =====
class BookingQueue {
    Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

// ===== Inventory Service =====
class InventoryService {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrease(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// ===== Booking Service =====
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> allocationMap = new HashMap<>();

    public void processBookings(BookingQueue queue, InventoryService inventory) {

        System.out.println("---- Processing Bookings ----\n");

        int idCounter = 1;

        while (queue.hasRequests()) {

            Reservation r = queue.getNextRequest();

            if (inventory.getAvailability(r.roomType) > 0) {

                // Generate unique room ID
                String roomId = r.roomType.replace(" ", "") + "-" + idCounter++;

                // Ensure uniqueness
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    // Map room type to allocated IDs
                    allocationMap.putIfAbsent(r.roomType, new HashSet<>());
                    allocationMap.get(r.roomType).add(roomId);

                    // Decrease inventory
                    inventory.decrease(r.roomType);

                    // Confirm booking
                    System.out.println("Booking Confirmed!");
                    System.out.println("Guest: " + r.guestName);
                    System.out.println("Room Type: " + r.roomType);
                    System.out.println("Room ID: " + roomId + "\n");

                }

            } else {
                System.out.println("Booking Failed (No Availability)");
                System.out.println("Guest: " + r.guestName);
                System.out.println("Room Type: " + r.roomType + "\n");
            }
        }
    }
}

// ===== Main Class =====
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====\n");

        // Step 1: Create Queue
        BookingQueue queue = new BookingQueue();

        queue.addRequest(new Reservation("Aarushi", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Single Room")); // will fail

        queue.addRequest(new Reservation("Kiran", "Double Room"));
        queue.addRequest(new Reservation("Meena", "Suite Room"));

        // Step 2: Inventory
        InventoryService inventory = new InventoryService();

        // Step 3: Booking Service
        BookingService service = new BookingService();

        // Step 4: Process bookings
        service.processBookings(queue, inventory);

        System.out.println("===== Done =====");
    }
}
