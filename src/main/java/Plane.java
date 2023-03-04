import java.util.ArrayList;

/**
 * A plane which has three different classes of {@code PlaneSeat}s.
 * @author Jonathan Stewart Thomas
 * @version 1.0.0.230304
 */
public class Plane {
    private final ArrayList<PlaneSeat> firstClassSeats;
    private final ArrayList<PlaneSeat> economyPlusSeats;
    private final ArrayList<PlaneSeat> economySeats;

    /**
     * Creates a {@code Plane} and initializes the {@code ArrayList}s for
     * each seat class type.
     */
    public Plane() {
        firstClassSeats = new ArrayList<>();
        economyPlusSeats = new ArrayList<>();
        economySeats = new ArrayList<>();
    }

    /**
     * Adds a {@code PlaneSeat} to this {@code Plane}.
     * @param planeSeat the {@code PLaneSeat} being added
     */
    public void add(PlaneSeat planeSeat) {
        if (planeSeat.getType() == PlaneSeat.Type.FIRST_CLASS) {
            firstClassSeats.add(planeSeat);
        }
        else if (planeSeat.getType() == PlaneSeat.Type.ECONOMY_PLUS) {
            economyPlusSeats.add(planeSeat);
        }
        else economySeats.add(planeSeat);
    }

    /**
     * Gets {@code PlaneSeat}s of a specific {@code Type}.
     * @param type the {@code Type} of {@code PlaneSeat}s we want
     * @return the {@code ArrayList} of {@code PlaneSeat}s of the particular {@code Type}
     */
    public ArrayList<PlaneSeat> getSeats(PlaneSeat.Type type) {
        if (type == PlaneSeat.Type.FIRST_CLASS) return firstClassSeats;
        else if (type == PlaneSeat.Type.ECONOMY_PLUS) return economyPlusSeats;
        else return economySeats;
    }

    /**
     * Outputs {@code Plane} as a {@code String}.
     * @return the {@code String} of this {@code Plane}
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("First Class (Price: $1000/seat)");
        int currentRow = 0;
        for (PlaneSeat seat : firstClassSeats) {
            if (seat.getRow() != currentRow) {
                currentRow = seat.getRow();
                stringBuilder.append(String.format("\n%s: ", currentRow));
            }
            if (!seat.isReserved()) stringBuilder.append(String.format("%s ", seat));
        }
        stringBuilder.append("\n\nEconomy Plus (Price: $500/seat)");
        for (PlaneSeat seat : economyPlusSeats) {
            if (seat.getRow() != currentRow) {
                currentRow = seat.getRow();
                stringBuilder.append(String.format("\n%s: ", currentRow));
            }
            if (!seat.isReserved()) stringBuilder.append(String.format("%s ", seat));
        }
        stringBuilder.append("\n\nEconomy (Price: $250/seat)");
        for (PlaneSeat seat : economySeats) {
            if (seat.getRow() != currentRow) {
                currentRow = seat.getRow();
                stringBuilder.append(String.format("\n%s: ", currentRow));
            }
            if (!seat.isReserved()) stringBuilder.append(String.format("%s ", seat));
        }
        return stringBuilder.toString();
    }
}
