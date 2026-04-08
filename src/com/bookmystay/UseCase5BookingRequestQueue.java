import java.util.*;

// ===== Reservation Class =====
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

// ===== Booking Queue =====
class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request (enqueue)
    public void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Request added:");
        r.display();
        System.out.println();
    }

    // Show all requests (without removing)
    public void showQueue() {
        System.out.println("---- Booking Requests (FIFO Order) ----\n");

        for (Reservation r : queue) {
            r.display();
        }
    }
}

// ===== Main Class =====
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====\n");

        // Create booking queue
        BookingQueue bookingQueue = new BookingQueue();

        // Simulate guest requests
        bookingQueue.addRequest(new Reservation("Aarushi", "Single Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Double Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Suite Room"));

        // Display queue (FIFO order)
        bookingQueue.showQueue();

        System.out.println("\n===== Requests Stored Successfully =====");
    }
}

