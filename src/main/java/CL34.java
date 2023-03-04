import java.util.HashMap;
import java.util.TreeSet;

/**
 * A CL34 that holds reservations for users of {@code ReservationSystem}.
 * {@code PlaneSeat}s can be added and removed from the reservations for
 * a particular {@code User}. A {@code TreeSet} of {@code PlaneSeat}s can
 * be returned for a particular {@code User}.
 * @author Jonathan Stewart Thonas
 * @version 1.0.0.230304
 */
public class CL34 {
    private final HashMap<String, TreeSet<PlaneSeat>> reservations;

    /**
     * Creates a {@code CL34} which initializes the {@code reservations HashMap}.
     */
    public CL34() {
        reservations = new HashMap<>();
    }

    /**
     * Adds a reservation for the {@code User}.
     * @param userName the user's name that is reserving the seat
     * @param planeSeat the {@code PlaneSeat} the {@code User} is reserving
     */
    public void add(String userName, PlaneSeat planeSeat) {
        TreeSet<PlaneSeat> reservedSeats = reservations.computeIfAbsent(userName, k -> new TreeSet<>());
        reservedSeats.add(planeSeat);
        planeSeat.reserve(userName);
    }

    /**
     * Cancels a reservation for the {@code User}.
     * @param userName the user's name that is canceling the seat
     * @param planeSeat the {@code PlaneSeat} the {@code User} is canceling
     */
    public void cancel(String userName, PlaneSeat planeSeat) {
        TreeSet<PlaneSeat> planeSeats = reservations.get(userName);
        if (planeSeats == null) {
            return;
        }
        if (planeSeats.remove(planeSeat)) {
            planeSeat.cancelReservation();
        }
    }

    /**
     * Gets the {@code TreeSet} of {@code PlaneSeat}s that are reserved for
     * {@code User}.
     * @param user the {@code User} we are getting the {@code PlaneSeat}s for.
     * @return the {@code TreeSet} of {@code PlaneSeat}s that are reserved for
     * {@code User}.
     */
    public TreeSet<PlaneSeat> getSeatsFor(User user) {
        return reservations.get(user.name());
    }
}
