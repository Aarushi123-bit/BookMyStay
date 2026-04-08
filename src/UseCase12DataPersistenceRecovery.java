package com.bookmystay.uc12;

import java.io.*;
import java.util.*;

// ===== Reservation (Serializable) =====
class Reservation implements Serializable {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(guestName + " booked " + roomType);
    }
}

// ===== System State (Serializable) =====
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// ===== Persistence Service =====
class PersistenceService {

    private static final String FILE_NAME = "system_data.ser";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.\n");

        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.\n");
            return (SystemState) in.readObject();

        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.\n");
            return null;
        }
    }
}

// ===== Main Class =====
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====\n");

        // Try loading existing data
        SystemState state = PersistenceService.load();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state != null) {
            inventory = state.inventory;
            bookings = state.bookings;
        } else {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            bookings = new ArrayList<>();
        }

        // Simulate booking
        Reservation r1 = new Reservation("Aarushi", "Single Room");
        bookings.add(r1);
        inventory.put("Single Room", inventory.get("Single Room") - 1);

        System.out.println("Current Bookings:");
        for (Reservation r : bookings) {
            r.display();
        }

        System.out.println("\nCurrent Inventory: " + inventory);

        // Save state before exit
        PersistenceService.save(new SystemState(inventory, bookings));

        System.out.println("===== Done =====");
    }
}