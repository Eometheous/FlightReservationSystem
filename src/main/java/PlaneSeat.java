public class PlaneSeat implements Comparable<PlaneSeat> {
    private final int row;
    private final char column;
    private final Type type;
    private double cost;
    private boolean reserved;

    @Override
    public int compareTo(PlaneSeat o) {
        if (row < o.row) return -1;
        else if (row > o.row) return 1;
        return Character.compare(column, o.column);
    }

    public enum Type {
        FIRST_CLASS,
        ECONOMY_PLUS,
        ECONOMY
    }

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
    }
    public void reserve() {
        reserved = true;
    }
    public void cancelReservation() {
        reserved = false;
    }

    public boolean isReserved() {
        return reserved;
    }

    public int getRow() {return row;}
    public char getColumn() {return column;}
    public Type getType() {return type;}
    public double getCost() {return cost;}

    @Override
    public String toString() {
        return String.format("%s", column);
    }
}
