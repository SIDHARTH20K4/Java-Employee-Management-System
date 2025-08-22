import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    private static void inputEmployeeDetails(Scanner scanner, EmployeeManager manager) {
        System.out.print("Enter First Name: ");
        String firstname = scanner.nextLine().trim();
        if (firstname.isEmpty()) {
            System.out.println("First name cannot be empty.");
            return;
        }

        System.out.print("Enter Last Name: ");
        String lastname = scanner.nextLine().trim();
        if (lastname.isEmpty()) {
            System.out.println("Last name cannot be empty.");
            return;
        }

        System.out.println("Choose Role:");
        Role[] roles = Role.values();
        for (int i = 0; i < roles.length; i++) {
            System.out.println((i + 1) + ". " + roles[i]);
        }
        int rIdx = parseIntOrDefault(scanner.nextLine().trim(), -1) - 1;
        Role role = (rIdx >= 0 && rIdx < roles.length) ? roles[rIdx] : null;
        if (role == null) {
            System.out.println("Invalid role.");
            return;
        }

        System.out.println("Choose Department:");
        Department[] departments = Department.values();
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i]);
        }
        int dIdx = parseIntOrDefault(scanner.nextLine().trim(), -1) - 1;
        Department department = (dIdx >= 0 && dIdx < departments.length) ? departments[dIdx] : null;
        if (department == null) {
            System.out.println("Invalid department.");
            return;
        }

        System.out.print("Enter ReportingTo ID (or 0): ");
        String reportingTo = scanner.nextLine().trim();

        System.out.println(manager.addEmployee(firstname, lastname, role, department, reportingTo));
    }

    private static void handleCheckIn(Scanner scanner, EmployeeManager manager) {
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Employee ID cannot be empty.");
            return;
        }

        System.out.print("Enter Check-in DateTime (yyyy-MM-dd HH:mm) or press Enter for current: ");
        String dtStr = scanner.nextLine().trim();
        LocalDateTime dt;
        if (dtStr.isEmpty()) {
            dt = LocalDateTime.now();
        } else {
            try {
                dt = LocalDateTime.parse(dtStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (DateTimeException e) {
                System.out.println("Invalid datetime format.");
                return;
            }
        }
        System.out.println(manager.checkIn(id, dt));
    }

    private static void handleCheckOut(Scanner scanner, EmployeeManager manager) {
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Employee ID cannot be empty.");
            return;
        }

        System.out.print("Enter Check-out DateTime (yyyy-MM-dd HH:mm) or press Enter for current: ");
        String dtStr = scanner.nextLine().trim();
        LocalDateTime dt;
        if (dtStr.isEmpty()) {
            dt = LocalDateTime.now();
        } else {
            try {
                dt = LocalDateTime.parse(dtStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (DateTimeException e) {
                System.out.println("Invalid datetime format.");
                return;
            }
        }
        System.out.println(manager.checkOut(id, dt));
    }

    private static void printAttendanceLog(Scanner scanner, EmployeeManager manager) {
        System.out.print("Enter date to view log (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine().trim();
        LocalDate date;
        if (dateStr.isEmpty()) {
            date = LocalDate.now();
        } else {
            try {
                date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeException e) {
                System.out.println("Invalid date.");
                return;
            }
        }
        System.out.println(manager.printLog(date));
    }

    private static void showWorkingHoursSummary(Scanner scanner, EmployeeManager manager) {
        System.out.print("Enter Start Date (yyyy-MM-dd): ");
        String startStr = scanner.nextLine().trim();
        System.out.print("Enter End Date (yyyy-MM-dd): ");
        String endStr = scanner.nextLine().trim();
        try {
            LocalDate start = LocalDate.parse(startStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate end = LocalDate.parse(endStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            System.out.println(manager.workingHoursSummary(start, end));
        } catch (DateTimeException e) {
            System.out.println("Invalid date format.");
        }
    }

    private static void handleDeleteEmployee(Scanner scanner, EmployeeManager manager) {
        System.out.print("Enter Employee ID to delete: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Employee ID cannot be empty.");
            return;
        }
        System.out.println(manager.deleteEmployee(id));
    }

    private static void handleDeleteAttendanceLog(Scanner scanner, EmployeeManager manager) {
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter Check-in DateTime of the log to delete (yyyy-MM-dd HH:mm): ");
        String dtStr = scanner.nextLine().trim();
        LocalDateTime checkIn;
        try {
            checkIn = LocalDateTime.parse(dtStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeException e) {
            System.out.println("Invalid datetime format.");
            return;
        }

        String result = manager.deleteAttendanceLog(id, checkIn);
        System.out.println(result);
    }

    private static void handleListCurrentlyCheckedIn(EmployeeManager manager) {
        System.out.println("Employees Currently Checked-in without Check-out:");
        System.out.println(manager.listCurrentlyCheckedInEmployees());
    }

    private static int parseIntOrDefault(String input, int defaultVal) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println(manager.preloadSampleEmployees());

        while (true) {
            System.out.println("""
                    Employee Attendance System
                 1. Add Employee
                 2. List Employees
                 3. Check-in
                 4. Check-out
                 5. Print Attendance Log
                 6. Delete Employees
                 7. List of Employees Currently CheckedIn
                 8. Working Hours Summary
                 9. Delete Attendance Log
                 10. Exit
            """);

            System.out.print("Choose option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> inputEmployeeDetails(scanner, manager);
                case "2" -> System.out.println(manager.listEmployees());
                case "3" -> handleCheckIn(scanner, manager);
                case "4" -> handleCheckOut(scanner, manager);
                case "5" -> printAttendanceLog(scanner, manager);
                case "6" -> handleDeleteEmployee(scanner, manager);
                case "7" -> handleListCurrentlyCheckedIn(manager);
                case "8" -> showWorkingHoursSummary(scanner, manager);
                case "9" -> handleDeleteAttendanceLog(scanner, manager);
                case "10" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}