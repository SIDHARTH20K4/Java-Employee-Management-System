import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EmployeeManager {
    private EmployeeList employeeList = new EmployeeList();
    private AttendanceList attendanceList = new AttendanceList();

    public String addEmployee(String firstName, String lastName, Role role, Department department, String reportingTo) {
        Employee emp = new Employee(firstName, lastName, role, department, reportingTo);
        if (!emp.validate()) {
            return emp.getValidationError();
        }
        employeeList.add(emp);
        return "Employee added: " + emp.getId();
    }

    public String listEmployees() {
        if (employeeList.isEmpty()) return "No employees found.";
        return employeeList.stream().map(Employee::toString).reduce((a, b) -> a + "\n" + b).orElse("");
    }

    public String checkIn(String id, LocalDateTime time) {
        if (employeeList.stream().noneMatch(e -> e.getId().equals(id))) {
            return "Invalid employee ID.";
        }
        Attendance att = new Attendance(id, time);
        String error = attendanceList.isValidCheckIn(att);
        if (!error.isEmpty()) {
            return error;
        } else {
            attendanceList.add(att);
            return "Checked in at " + time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
    }

    public String checkOut(String id, LocalDateTime time) {
        if (employeeList.stream().noneMatch(e -> e.getId().equals(id))) {
            return "Invalid employee ID.";
        }

        AbstractMap.SimpleEntry<String, Attendance> result = attendanceList.validateCheckOut(id, time);
        String error = result.getKey();
        Attendance attendance = result.getValue();

        if (!error.isEmpty()) {
            return "Check-out failed: " + error;
        }
        return attendance.checkout(time);
    }

    public String printLog(LocalDate date) {
        List<Attendance> found = attendanceList.stream()
                .filter(a -> a.getCheckIn().toLocalDate().equals(date))
                .toList();

        if (found.isEmpty()) return "No logs for " + date;
        return found.stream().map(Attendance::toString).reduce((a, b) -> a + "\n" + b).orElse("");
    }

    public String deleteEmployee(String id) {
        return employeeList.delete(id) ? "Employee deleted." : "Employee not found.";
    }

    public String deleteAttendanceLog(String employeeId, LocalDateTime checkIn) {
        Attendance log = attendanceList.stream()
                .filter(a -> a.getEmployeeId().equals(employeeId) && a.getCheckIn().equals(checkIn))
                .findFirst()
                .orElse(null);

        if (log == null) return "No matching attendance log found.";
        return attendanceList.delete(log) ? "Attendance log deleted successfully." : "Failed to delete attendance log.";
    }

    public String listCurrentlyCheckedInEmployees() {
        List<Attendance> pending = attendanceList.stream()
                .filter(a -> a.getCheckOut() == null)
                .toList();

        if (pending.isEmpty()) return "No employees are currently checked-in.";
        return "Currently checked-in employees:\n" +
                pending.stream().map(Attendance::toString).reduce((a, b) -> a + "\n" + b).orElse("");
    }

    public String workingHoursSummary(LocalDate startDate, LocalDate endDate) {
        Map<String, Long> summary = attendanceList.getWorkingHoursSummary(startDate, endDate);
        if (summary.isEmpty()) {
            return "No attendance records found in this date range.";
        }

        StringBuilder result = new StringBuilder("Working Hours Summary from " + startDate + " to " + endDate + ":\n");
        for (Map.Entry<String, Long> entry : summary.entrySet()) {
            String id = entry.getKey();
            long totalMinutes = entry.getValue();

            Employee emp = employeeList.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
            long hours = totalMinutes / 60;
            long mins = totalMinutes % 60;
            String name = (emp != null) ? emp.getFirstName() + " " + emp.getLastName() : "Unknown";

            result.append("ID: ").append(id)
                    .append(" | Name: ").append(name)
                    .append(" | Worked: ").append(hours).append("h ").append(mins).append("m\n");
        }
        return result.toString().trim();
    }

    public String preloadSampleEmployees() {
        List<String> messages = new ArrayList<>();

        Employee admin = new Employee("Admin", "User", Role.MANAGER, Department.ADMIN, "0");
        admin.setId("E001"); // manually assign ID like Kotlin version
        if (admin.validate()) {
            employeeList.add(admin);
            messages.add("Admin created with ID: " + admin.getId());
        }

        List<Employee> sampleEmployees = List.of(
                new Employee("Emma", "Watson", Role.DEVELOPER, Department.ENGINEERING, "E001"),
                new Employee("John", "Doe", Role.HR, Department.HR, "E001"),
                new Employee("Ravi", "P", Role.MANAGER, Department.SALES, "E001"),
                new Employee("Divya", "S", Role.INTERN, Department.MARKETING, "E001"),
                new Employee("John", "Smith", Role.DEVELOPER, Department.ENGINEERING, "E001")
        );

        for (Employee emp : sampleEmployees) {
            if (emp.validate()) {
                employeeList.add(emp);
            } else {
                messages.add("Error in preloaded employee: " + emp.getValidationError());
            }
        }
        return String.join("\n", messages);
    }
}