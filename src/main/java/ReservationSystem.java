import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ReservationSystem {
    private static Plane plane;
    private static CL34 cl34;
    private static HashMap<String, User> userLogins;
    private static ArrayList<String> adminIDs;
    private static Scanner stdio;
    public static void main(String[] args) {
        // generate Plane from plane.txt
        generatePlane();
        // generate CL34 from arg[0]
        loadCL34(args[0]);
        // get list of Users from arg[1]
        loadUserList(args[1]);
        // get adminID's
        loadAdminIDs();

        // everything is loaded
        stdio = new Scanner(System.in);
        String choice = "";
        do {
            System.out.println("[L]ogin [S]ign up [E]xit");
            choice = stdio.nextLine().toUpperCase();
        } while (!choice.equals("L") && !choice.equals("S") && !choice.equals("E"));
        User user = null;
        switch (choice) {
            case "L" -> user = login();
            case "S" -> user = signUp();
        }
        assert user != null;
        System.out.printf("Welcome %s", user.getName());
        System.out.println("Check [A]availability  Make [R]eservation  [C]ancel Reservation   [V]iew Reservations  [D]one");
        do {
            choice = stdio.nextLine().toUpperCase();
            switch ((choice)) {
                case "A" -> System.out.println(plane);

            }
        } while (!choice.equals("D"));
        stdio.close();
    }

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
    private static void loadCL34(String arg) {
        cl34 = new CL34();
        Scanner scanner;
        try {
            scanner = new Scanner(new File(arg));
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
                if (type == 'f') {
                    planeSeats = plane.getSeats(PlaneSeat.Type.FIRST_CLASS);
                    for (PlaneSeat seat : planeSeats) {
                        if (row == seat.getRow() && column == seat.getColumn()) {
                            planeSeat = seat;
                        }
                    }
                }
                else if (type == '+') {
                    planeSeats = plane.getSeats(PlaneSeat.Type.ECONOMY_PLUS);
                    for (PlaneSeat seat : planeSeats) {
                        if (row == seat.getRow() && column == seat.getColumn()) {
                            planeSeat = seat;
                        }
                    }
                }
                else {
                    planeSeats = plane.getSeats(PlaneSeat.Type.ECONOMY_PLUS);
                    for (PlaneSeat seat : planeSeats) {
                        if (row == seat.getRow() && column == seat.getColumn()) {
                            planeSeat = seat;
                        }
                    }
                }
                if (planeSeat != null)  {
                    cl34.add(name, planeSeat);
                }
            }
        }
        scanner.close();
    }
    private static void loadUserList(String arg) {
        userLogins = new HashMap<>();
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
    private static User login() {
        while (true) {
            System.out.println("Enter email:");
            String email = stdio.nextLine();
            System.out.println("Enter password:");
            String password = stdio.nextLine();
            if (userLogins.containsKey(email)) {
                User user = userLogins.get(email);
                if (user.getPassword().equals(password)) return user;
                else System.out.println("Wrong email/password");
            }
        }
    }
    private static User signUp() {
        System.out.println("Enter email:");
        String email = stdio.nextLine();
        if (userLogins.containsKey(email)) {
            System.out.println("An account already exists with this email.");
            System.out.println("Please login");
            return login();
        }
        System.out.println("Enter full name:");
        String name = stdio.nextLine();
        while (true) {
            System.out.println("Enter password:");
            String password1 = stdio.nextLine();
            System.out.println("Re enter password:");
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
    private static void makeReservation() {
        System.out.println(plane);
        System.out.println("""
            Reserve...
            [F]irst class seat
            Economy [P]lus seat
            [E]conomy seat
            """);
        String choice = "";
        do {
            choice = stdio.nextLine().toUpperCase();
        } while(!choice.equals("F") && !choice.equals("P") && !choice.equals("E"));
        if (choice.equals("F")) {
            ArrayList<PlaneSeat> seats = plane.getSeats(PlaneSeat.Type.FIRST_CLASS);
            System.out.println("Enter a seat to reserve (row column");
            int row = stdio.nextInt();
            char column = stdio.next().charAt(0);

        }
    }
}
