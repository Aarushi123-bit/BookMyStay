package com.bookmystay.uc9;

import java.util.*;

// ===== Custom Exception =====
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ===== Validator Class =====
class BookingValidator {

    private static final Set<String> validRoomTypes =
            new HashSet<>(Arrays.asList("Single Room", "Double Room", "Suite Room"));

    // Validate booking input
    public static void validate(String roomType, int available)
            throws InvalidBookingException {

        // Check valid room type
        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Check availability
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }
}

// ===== Inventory =====
class Inventory {
    private Map<String, Integer> data = new HashMap<>();

    public Inventory() {
        data.put("Single Room", 1);
        data.put("Double Room", 1);
        data.put("Suite Room", 0);
    }

    public int getAvailability(String type) {
        return data.getOrDefault(type, 0);
    }

    public void decrease(String type) {
        data.put(type, data.get(type) - 1);
    }
}

// ===== Main Class =====
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====\n");

        Inventory inventory = new Inventory();

        // Test cases (valid + invalid)
        String[] testRequests = {
                "Single Room",
                "Suite Room",     // no availability
                "Luxury Room"     // invalid type
        };

        for (String roomType : testRequests) {

            try {
                System.out.println("Processing booking for: " + roomType);

                int available = inventory.getAvailability(roomType);

                // Validate input
                BookingValidator.validate(roomType, available);

                // If valid → proceed
                inventory.decrease(roomType);

                System.out.println("Booking Successful for " + roomType + "\n");

            } catch (InvalidBookingException e) {

                // Graceful failure
                System.out.println("Booking Failed: " + e.getMessage() + "\n");
            }
        }

        System.out.println("===== System Running Safely =====");
    }
}
