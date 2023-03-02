import java.util.ArrayList;

public class Plane {
    private final ArrayList<PlaneSeat> firstClassSeats;
    private final ArrayList<PlaneSeat> economyPlusSeats;
    private final ArrayList<PlaneSeat> economySeats;
    public Plane() {
        firstClassSeats = new ArrayList<>();
        economyPlusSeats = new ArrayList<>();
        economySeats = new ArrayList<>();
    }

    public void add(PlaneSeat planeSeat) {
        if (planeSeat.getType() == PlaneSeat.Type.FIRST_CLASS) {
            firstClassSeats.add(planeSeat);
        }
        else if (planeSeat.getType() == PlaneSeat.Type.ECONOMY_PLUS) {
            economyPlusSeats.add(planeSeat);
        }
        else economySeats.add(planeSeat);
    }

    public ArrayList<PlaneSeat> getSeats(PlaneSeat.Type type) {
        if (type == PlaneSeat.Type.FIRST_CLASS) return firstClassSeats;
        else if (type == PlaneSeat.Type.ECONOMY_PLUS) return economyPlusSeats;
        else return economySeats;
    }

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
