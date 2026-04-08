import java.util.*;

// ===== Service Class =====
class Service {
    String name;
    double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }
}

// ===== Add-On Service Manager =====
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<Service>> serviceMap = new HashMap<>();

    // Add service
    public void addService(String reservationId, Service service) {

        if (!serviceMap.containsKey(reservationId)) {
            serviceMap.put(reservationId, new ArrayList<>());
        }

        serviceMap.get(reservationId).add(service);

        System.out.println("Service added: " + service.name + " for " + reservationId);
    }

    // Display services
    public void displayServices(String reservationId) {

        if (!serviceMap.containsKey(reservationId)) {
            System.out.println("No services for " + reservationId);
            return;
        }

        System.out.println("\nServices for " + reservationId + ":");

        for (Service s : serviceMap.get(reservationId)) {
            System.out.println("- " + s.name + " (₹" + s.cost + ")");
        }
    }

    // Calculate total cost
    public double getTotalCost(String reservationId) {

        double total = 0;

        if (serviceMap.containsKey(reservationId)) {
            for (Service s : serviceMap.get(reservationId)) {
                total += s.cost;
            }
        }

        return total;
    }
}

// ===== Main Class =====
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====\n");

        // Example Reservation IDs (from Use Case 6)
        String r1 = "SingleRoom-1";
        String r2 = "DoubleRoom-2";

        // Create manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services
        manager.addService(r1, new Service("Breakfast", 200));
        manager.addService(r1, new Service("WiFi", 100));
        manager.addService(r2, new Service("Airport Pickup", 500));

        // Display services
        manager.displayServices(r1);
        manager.displayServices(r2);

        // Show total cost
        System.out.println("\nTotal cost for " + r1 + ": ₹" + manager.getTotalCost(r1));
        System.out.println("Total cost for " + r2 + ": ₹" + manager.getTotalCost(r2));

        System.out.println("\n===== Done =====");
    }
}
