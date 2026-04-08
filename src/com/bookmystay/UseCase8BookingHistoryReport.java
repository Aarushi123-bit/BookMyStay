package com.bookmystay.uc8;

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

// ===== Booking History =====
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all bookings
    public List<Reservation> getHistory() {
        return history;
    }
}

// ===== Report Service =====
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> history) {

        System.out.println("---- Booking History ----\n");

        for (Reservation r : history) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> history) {

        System.out.println("\n---- Booking Summary ----\n");

        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history) {
            countMap.put(r.roomType,
                    countMap.getOrDefault(r.roomType, 0) + 1);
        }

        for (String type : countMap.keySet()) {
            System.out.println(type + ": " + countMap.get(type) + " bookings");
        }
    }
}

// ===== Main Class =====
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====\n");

        // Step 1: Booking History
        BookingHistory history = new BookingHistory();

        // Step 2: Add confirmed bookings (simulate UC6 output)
        history.addReservation(new Reservation("Aarushi", "Single Room", "SingleRoom-1"));
        history.addReservation(new Reservation("Rahul", "Double Room", "DoubleRoom-2"));
        history.addReservation(new Reservation("Priya", "Single Room", "SingleRoom-3"));

        // Step 3: Report Service
        BookingReportService report = new BookingReportService();

        // Step 4: Show history
        report.showAllBookings(history.getHistory());

        // Step 5: Summary report
        report.generateSummary(history.getHistory());

        System.out.println("\n===== Done =====");
    }
}
