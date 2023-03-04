/**
 * A plane seat containing a row, column, and user reserving it.
 * There are three {@code Type}s of plane seats, first class,
 * economy plus, and economy.
 * @author Jonathan Stewart Thomas
 * @version 1.0.0.230304
 */
public class PlaneSeat implements Comparable<PlaneSeat> {
    private final int row;
    private final char column;
    private final Type type;
    private double cost;
    private boolean reserved;
    private String user;

    /**
     * Compares {@code PlaneSeat}s .
     * @param o the object to be compared.
     * @return -1 if before, 1 if after, 0 if equal
     */
    @Override
    public int compareTo(PlaneSeat o) {
        if (row < o.row) return -1;
        else if (row > o.row) return 1;
        return Character.compare(column, o.column);
    }

    /**
     * The three different {@code Type}s of {@code PlaneSeat}.
     */
    public enum Type {
        FIRST_CLASS,
        ECONOMY_PLUS,
        ECONOMY
    }

    /**
     * Creates a {@code PlaneSeat} with a row, column, and type.
     * @param column the column the seat is in
     * @param row the row the seat is in
     * @param type the type of seat this is
     */
    public PlaneSeat(char column, int row, Type type) {
      this.row = row;
      this.column = column;
        this.type = type;
        switch (type) {
            case FIRST_CLASS -> cost = 1000;
            case ECONOMY_PLUS -> cost = 500;
            case ECONOMY -> cost = 250;
        }
        reserved = false;
        user = null;
    }

    /**
     * Reserves a seat for {@code User}.
     * @param user the {@code User} reserving the seat
     */
    public void reserve(String user) {
        this.user = user;
        reserved = true;
    }

    /**
     * Cancels the reservation for this {@code PlaneSeat}.
     */
    public void cancelReservation() {
        user = null;
        reserved = false;
    }

    /**
     * Checks if this {@code PlaneSeat} is reserved
     * @return true if it is reserved
     */
    public boolean isReserved() {
        return reserved;
    }

    /**
     * Gets the row of this {@code PlaneSeat}.
     * @return row of this {@code PlaneSeat}
     */
    public int getRow() {return row;}

    /**
     * Gets the column of this {@code PlaneSeat}.
     * @return column of this {@code PlaneSeat}
     */
    public char getColumn() {return column;}

    /**
     * Gets the type of this {@code PlaneSeat}.
     * @return type of this {@code PlaneSeat}
     */
    public Type getType() {return type;}

    /**
     * Gets the cost of this {@code PlaneSeat}.
     * @return cost of this {@code PlaneSeat}
     */
    public double getCost() {return cost;}

    /**
     * Gets the {@code User} reserving this {@code PlaneSeat}.
     * @return {@code User} reserving this {@code PlaneSeat}
     */
    public String getUser() {return user;}

    /**
     * Outputs this {@code PlaneSeat} as a {@code String}.
     * @return {@code String} of this {@code PlaneSeat}.
     */
    @Override
    public String toString() {
        return String.format("%s%s ", row, column);
    }
}
