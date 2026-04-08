package com.bookmystay.uc10;

import java.util.*;

// ===== Reservation Class =====
class Reservation {
    String guestName;
    String roomType;
    String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public void display() {
        System.out.println("Guest: " + guestName +
                " | Room: " + roomType +
                " | Room ID: " + roomId);
    }
}

// ===== Inventory =====
class Inventory {
    private Map<String, Integer> data = new HashMap<>();

    public Inventory() {
        data.put("Single Room", 1);
        data.put("Double Room", 1);
    }

    public void increase(String type) {
        data.put(type, data.getOrDefault(type, 0) + 1);
    }

    public void display() {
        System.out.println("\nCurrent Inventory:");
        for (String key : data.keySet()) {
            System.out.println(key + ": " + data.get(key));
        }
    }
}

// ===== Cancellation Service =====
class CancellationService {

    private Map<String, Reservation> activeBookings = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();

    // Add booking (simulate confirmed booking)
    public void addBooking(Reservation r) {
        activeBookings.put(r.roomId, r);
    }

    // Cancel booking
    public void cancel(String roomId, Inventory inventory) {

        if (!activeBookings.containsKey(roomId)) {
            System.out.println("Cancellation Failed: Booking not found for " + roomId);
            return;
        }

        Reservation r = activeBookings.get(roomId);

        // Step 1: Push to rollback stack
        rollbackStack.push(roomId);

        // Step 2: Restore inventory
        inventory.increase(r.roomType);

        // Step 3: Remove booking
        activeBookings.remove(roomId);

        System.out.println("Cancellation Successful for Room ID: " + roomId);
    }

    // Show rollback stack
    public void showRollbackStack() {
        System.out.println("\nRollback Stack (LIFO): " + rollbackStack);
    }
}

// ===== Main Class =====
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====\n");

        Inventory inventory = new Inventory();
        CancellationService service = new CancellationService();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("Aarushi", "Single Room", "R101");
        Reservation r2 = new Reservation("Rahul", "Double Room", "R102");

        service.addBooking(r1);
        service.addBooking(r2);

        // Cancel bookings
        service.cancel("R101", inventory);
        service.cancel("R999", inventory); // invalid case

        // Show rollback stack
        service.showRollbackStack();

        // Show updated inventory
        inventory.display();

        System.out.println("\n===== Done =====");
    }
}

