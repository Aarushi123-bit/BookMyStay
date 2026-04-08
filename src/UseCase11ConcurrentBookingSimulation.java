package com.bookmystay.uc11;

import java.util.*;

// ===== Reservation =====
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// ===== Thread-Safe Inventory =====
class Inventory {

    private Map<String, Integer> data = new HashMap<>();

    public Inventory() {
        data.put("Single Room", 1); // only 1 room to show conflict
    }

    // synchronized method (critical section)
    public synchronized boolean bookRoom(String roomType, String guestName) {

        int available = data.getOrDefault(roomType, 0);

        if (available > 0) {
            System.out.println(guestName + " is booking...");

            // simulate delay (to show race condition effect)
            try { Thread.sleep(100); } catch (Exception e) {}

            data.put(roomType, available - 1);

            System.out.println("Booking SUCCESS for " + guestName);
            return true;
        } else {
            System.out.println("Booking FAILED for " + guestName + " (No rooms)");
            return false;
        }
    }

    public void display() {
        System.out.println("\nFinal Inventory: " + data);
    }
}

// ===== Booking Thread =====
class BookingThread extends Thread {

    private Inventory inventory;
    private Reservation reservation;

    public BookingThread(Inventory inventory, Reservation reservation) {
        this.inventory = inventory;
        this.reservation = reservation;
    }

    public void run() {
        inventory.bookRoom(reservation.roomType, reservation.guestName);
    }
}

// ===== Main Class =====
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====\n");

        Inventory inventory = new Inventory();

        // Multiple users trying same room
        Reservation r1 = new Reservation("Aarushi", "Single Room");
        Reservation r2 = new Reservation("Rahul", "Single Room");
        Reservation r3 = new Reservation("Priya", "Single Room");

        // Threads (simulating concurrent users)
        BookingThread t1 = new BookingThread(inventory, r1);
        BookingThread t2 = new BookingThread(inventory, r2);
        BookingThread t3 = new BookingThread(inventory, r3);

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (Exception e) {}

        // Final state
        inventory.display();

        System.out.println("\n===== Done =====");
    }
}
