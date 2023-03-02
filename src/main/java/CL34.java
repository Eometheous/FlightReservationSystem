import java.util.HashMap;
import java.util.TreeSet;

public class CL34 {
    private final HashMap<String, TreeSet<PlaneSeat>> reservations;
    public CL34() {
        reservations = new HashMap<>();
    }

    public void add(String name, PlaneSeat planeSeat) {
        TreeSet<PlaneSeat> reservedSeats = reservations.computeIfAbsent(name, k -> new TreeSet<>());
        reservedSeats.add(planeSeat);
        planeSeat.reserve();
    }

    public boolean cancel(String personName, PlaneSeat planeSeat) {
        TreeSet<PlaneSeat> planeSeats = reservations.get(personName);
        if (planeSeats == null) {
            return false;
        }
        if (planeSeats.remove(planeSeat)) {
            planeSeat.cancelReservation();
            return true;
        }
        return false;
    }
}
