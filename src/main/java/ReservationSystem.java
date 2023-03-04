import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * <p>
 *     A reservation system where {@code User}s can reserve {@code PlaneSeat}s.
 *     This includes {@code main} and requires two arguments to run properly.
 *     It starts by generating a {@code Plane} by reading {@code PlaneSeat}s from {@code plane.txt}.
 *     Once the {@code Plane} is generated, {@code ReservationSystem} then loads the cl34 and userLogins
 *     from args[0] and args[1] respectively. The final thing to load is the adminID's from adminIDs.txt
 * </p>
 * <p>
 *     Once that everything is loaded, {@code User}s can choose from the two login options.
 * </p>
 * <p>
 *     Regular {@code User}s can either signup or login. Once they are signed in with their email and
 *     password, they can view available seats, make reservations, cancel reservations, view their
 *     reserved seats and balance due, and return to the login menu.
 * </p>
 * <p>
 *     Admin users must login with their admin ID. Once signed in, admin can view the manifest list
 *     which contains all of the reserved {@code PlaneSeat}s and the name of the {@code User}
 *     reserving it.
 * </p>
 * @author Jonathan Stewart Thomas
 * @version 1.0.0.230304
 */
public class ReservationSystem {
    private static Plane plane;
    private static CL34 cl34;
    private static HashMap<String, User> userLogins;
    private static ArrayList<String> adminIDs;
    private static Scanner stdio;

    /**
     * The main method of this program. It must be ran with two arguments.
     * @param args contains CL34.txt and Users.txt
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("""
                    This program requires 2 arguments to run properly.
                    The first argument contains the CL34.
                    The second argument contains all user information.
                    
                    
                    Below is an example of how this program should be ran.
                    java ReservationSystem CL34 Users
                    """);
            return;
        }
        // load everything from files into their data structures
        generatePlane();
        loadCL34(args[0]);
        loadUserList(args[1]);
        loadAdminIDs();

        stdio = new Scanner(System.in);
        String choice;
        do {
            System.out.println("[U]ser Login    [A]dmin Login   [E]xit");
            choice = stdio.nextLine().toUpperCase();
            if (choice.equals("U")) {
                userOptions();
            } else if (choice.equals("A")) {
                adminOptions();
            }
        } while (!choice.equals("E"));

        save(args);
        stdio.close();
    }

    /**
     * Options for regular {@code User}s.
     */
    private static void userOptions() {
        String choice;
        do {
            System.out.println("[L]ogin [S]ign up");
            choice = stdio.nextLine().toUpperCase();
        } while (!choice.equals("L") && !choice.equals("S"));
        User user = null;
        switch (choice) {
            case "L" -> user = login();
            case "S" -> user = signUp();
        }
        System.out.printf("Welcome %s\n", user.name());
        do {
            System.out.println("Check [A]availability  Make [R]eservation  [C]ancel Reservation   [V]iew Reservations  [D]one\n");
            choice = stdio.nextLine().toUpperCase();
            switch (choice) {
                case "A" -> System.out.println(plane);
                case "R" -> makeReservationFor(user);
                case "C" -> cancelReservationFor(user);
                case "V" -> viewReservationsFor(user);
            }
        } while (!choice.equals("D"));
    }

    /**
     * Asks a {@code User} to log in with their email and password.
     * @return the {@code User} containing that information from the {@code userLogins}
     */
    private static User login() {
        while (true) {
            System.out.print("Enter email: ");
            String email = stdio.nextLine();
            System.out.print("Enter password: ");
            String password = stdio.nextLine();
            if (userLogins.containsKey(email)) {
                User user = userLogins.get(email);
                if (user.password().equals(password)) return user;
            }
            System.out.println("Wrong email/password");
        }
    }

    /**
     * Asks a {@code User} to enter their email, full name, and password for their account.
     * @return {@code User} containing the entered information
     */
    private static User signUp() {
        System.out.print("Enter email: ");
        String email = stdio.nextLine();
        if (userLogins.containsKey(email)) {
            System.out.println("An account already exists with this email.");
            System.out.println("Please login");
            return login();
        }
        System.out.print("Enter full name: ");
        String name = stdio.nextLine();
        while (true) {
            System.out.print("Enter password: ");
            String password1 = stdio.nextLine();
            System.out.print("Re enter password: ");
            String password2 = stdio.nextLine();
            if (password1.equals(password2)) {
                User user = new User(email, name, password1);
                userLogins.put(email, user);
                return user;
            }
            else {
                System.out.println("Passwords don't match");
            }
        }
    }

    /**
     * Makes a reservation for {@code User}. They enter the type of {@code PlaneSeat} they
     * want to reserve and then the row and column of that seat.
     * @param user {@code User} reserving the seat
     */
    private static void makeReservationFor(User user) {
        System.out.println("""
            Reserve...
            [F]irst class seat
            Economy [P]lus seat
            [E]conomy seat
            """);
        String choice;
        do {
            choice = stdio.nextLine().toUpperCase();
        } while(!choice.equals("F") && !choice.equals("P") && !choice.equals("E"));

        System.out.println("Enter a seat to reserve (row column)");
        int row = stdio.nextInt();
        char column = stdio.next().toUpperCase().charAt(0);
        stdio.nextLine();
        if (choice.equals("F")) {
            for (PlaneSeat seat : plane.getSeats(PlaneSeat.Type.FIRST_CLASS)) {
                if (seat.getRow() == row && seat.getColumn() == column && !seat.isReserved()) {
                    cl34.add(user.name(), seat);
                    return;
                }
            }
        }
        else if (choice.equals("P")) {
            for (PlaneSeat seat : plane.getSeats(PlaneSeat.Type.ECONOMY_PLUS)) {
                if (seat.getRow() == row && seat.getColumn() == column && !seat.isReserved()) {
                    cl34.add(user.name(), seat);
                    return;
                }
            }
        }
        else {
            for (PlaneSeat seat : plane.getSeats(PlaneSeat.Type.ECONOMY)) {
                if (seat.getRow() == row && seat.getColumn() == column && !seat.isReserved()) {
                    cl34.add(user.name(), seat);
                    return;
                }
            }

        }
        System.out.println("This seat doesn't exist or is already reserved");
    }

    /**
     * Cancels a reservation for {@code User}. They enter the row and column of the seat they
     * want to cancel the reservation for.
     * @param user {@code User} canceling a seat reservation
     */
    private static void cancelReservationFor(User user) {
        TreeSet<PlaneSeat> seats = cl34.getSeatsFor(user);
        if (seats == null) {
            System.out.println("You haven't reserved any seats yet.");
            return;
        }

        System.out.printf("Seats reserved by %s\n", user.name());
        System.out.println(seats);
        System.out.println("Choose a seat to cancel (row column)");
        int row = stdio.nextInt();
        char column = stdio.next().toUpperCase().charAt(0);
        stdio.nextLine();
        for (PlaneSeat seat : seats) {
            if (seat.getRow() == row && seat.getColumn() == column) {
                cl34.cancel(user.name(), seat);
                return;
            }
        }
        System.out.println("This seat doesn't exist.");
    }

    /**
     * Displays all reservations for {@code User} and the balance due for those seats.
     * @param user {@code User} that is viewing their reserved seats.
     */
    private static void viewReservationsFor(User user) {
        System.out.println(user.name());
        double balanceDue = 0;
        TreeSet<PlaneSeat> seats = cl34.getSeatsFor(user);
        if (seats == null) {
            System.out.println("You haven't reserved any seats yet.");
            return;
        }
        System.out.println("Reserved Seats:");
        for (PlaneSeat seat : seats) {
            System.out.printf("%s $%.2f, ", seat, seat.getCost());
            balanceDue += seat.getCost();
        }
        System.out.printf("\nBalance due: $%.2f\n", balanceDue);
    }

    /**
     * The options for Admin {@code User}s
     */
    private static void adminOptions() {
        while (true) {
            System.out.print("Enter admin ID: ");
            String id = stdio.nextLine();
            if (adminIDs.contains(id)) {
                String choice;
                do {
                    System.out.println("Show [M]anifest list  [D]one");
                    choice = stdio.nextLine().toUpperCase();
                    if (choice.equals("M")) showManifest();
                } while (!choice.equals("D"));
                return;
            }
            else {
                System.out.println("Wrong Admin ID");
            }
        }
    }

    /**
     * Shows the manifest which displays all reserved seats, and who is reserving them.
     */
    private static void showManifest() {
        System.out.println("First Class Seats");
        for (PlaneSeat seat : plane.getSeats(PlaneSeat.Type.FIRST_CLASS)) {
            if (seat.isReserved()) System.out.printf("%s: %s\n", seat, seat.getUser());
        }
        System.out.println("Economy Plus Seats");
        for (PlaneSeat seat : plane.getSeats(PlaneSeat.Type.ECONOMY_PLUS)) {
            if (seat.isReserved()) System.out.printf("%s: %s\n", seat, seat.getUser());
        }
        System.out.println("Economy Seats");
        for (PlaneSeat seat : plane.getSeats(PlaneSeat.Type.ECONOMY)) {
            if (seat.isReserved()) System.out.printf("%s: %s\n", seat, seat.getUser());
        }
    }

    /**
     * Generates a {@code Plane} from plane.txt
     */
    private static void generatePlane() {
        plane = new Plane();
        Scanner scanner;
        try {
            scanner = new Scanner(new File("plane.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int row;
        char column;
        PlaneSeat planeSeat;
        while (scanner.hasNext()) {
            String[] seats = scanner.nextLine().split(" ");
            row = Integer.parseInt(seats[0]);
            for (int i = 1; i < seats.length; i++) {
                column = seats[i].charAt(0);
                char type = seats[i].charAt(1);
                if (type == 'f') planeSeat = new PlaneSeat(column, row, PlaneSeat.Type.FIRST_CLASS);
                else if (type == '+') planeSeat = new PlaneSeat(column, row, PlaneSeat.Type.ECONOMY_PLUS);
                else planeSeat = new PlaneSeat(column, row, PlaneSeat.Type.ECONOMY);
                plane.add(planeSeat);
            }
        }
        scanner.close();
    }

    /**
     * Loads the {@code cl34} from the first argument.
     * @param arg the {@code CL34.txt} argument
     */
    private static void loadCL34(String arg) {
        cl34 = new CL34();

        File cl34File = new File(arg);
        try {
            if (cl34File.createNewFile()) {
                System.out.printf("%s created successfully\n", arg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner scanner;
        try {
            scanner = new Scanner(cl34File);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (scanner.hasNext()) {
            String name = scanner.nextLine();
            CharSequence[] seatsInfo = scanner.nextLine().split(" ");
            for (CharSequence seatInfo : seatsInfo) {
                char column = seatInfo.charAt(0);
                int row = Integer.parseInt(seatInfo, 1, 3, 10);
                char type = seatInfo.charAt(3);
                ArrayList<PlaneSeat> planeSeats;
                PlaneSeat planeSeat = null;

                if (type == 'f') planeSeats = plane.getSeats(PlaneSeat.Type.FIRST_CLASS);
                else if (type == '+')  planeSeats = plane.getSeats(PlaneSeat.Type.ECONOMY_PLUS);
                else planeSeats = plane.getSeats(PlaneSeat.Type.ECONOMY);

                for (PlaneSeat seat : planeSeats) {
                    if (row == seat.getRow() && column == seat.getColumn()) {
                        planeSeat = seat;
                        break;
                    }
                }

                if (planeSeat != null)  {
                    cl34.add(name, planeSeat);
                }
            }
        }
        scanner.close();
    }

    /**
     * Loads the {@code userLogins} from the second argument
     * @param arg the {@code Users.txt} argument
     */
    private static void loadUserList(String arg) {
        userLogins = new HashMap<>();

        File userFile = new File(arg);
        try {
            if (userFile.createNewFile()) {
                System.out.printf("%s created successfully\n", arg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner scanner;
        try {
            scanner = new Scanner(new File(arg));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (scanner.hasNext()) {
            String email = scanner.nextLine();
            String name = scanner.nextLine();
            String password = scanner.nextLine();
            userLogins.put(email, new User(email, name, password));
        }
        scanner.close();
    }

    /**
     * Loads {@code adminIDs} from {@code adminIDs.txt}
     */
    private static void loadAdminIDs() {
        adminIDs = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = new Scanner(new File("adminIDs.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNext()) {
            adminIDs.add(scanner.nextLine());
        }
        scanner.close();
    }

    /**
     * Saves both {@code args} overwriting the original information.
     * @param args {@code args} entered when running the program
     */
    private static void save(String[] args) {
        File cl34File = new File(args[0]);
        File usersFile = new File(args[1]);
        try {
            BufferedWriter cl34FileWriter = new BufferedWriter(new FileWriter(cl34File));
            for (User user : User.list) {
                cl34FileWriter.write(user.name());
                cl34FileWriter.newLine();

                TreeSet<PlaneSeat> seats = cl34.getSeatsFor(user);
                if (seats != null) {
                    for (PlaneSeat seat : cl34.getSeatsFor(user)) {
                        cl34FileWriter.write(String.format("%s%02d",
                                seat.getColumn(), seat.getRow()));
                        if (seat.getType() == PlaneSeat.Type.FIRST_CLASS) {
                            cl34FileWriter.write("f ");
                        } else if (seat.getType() == PlaneSeat.Type.ECONOMY_PLUS) {
                            cl34FileWriter.write("+ ");
                        } else cl34FileWriter.write("e ");
                    }
                    cl34FileWriter.newLine();
                }
                cl34FileWriter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            BufferedWriter userFileWriter = new BufferedWriter(new FileWriter(usersFile));
            for (User user : User.list) {
                userFileWriter.write(user.toString());
            }
            userFileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
