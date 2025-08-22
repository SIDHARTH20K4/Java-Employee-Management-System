enum Role {
    HR, ENGINEER, MANAGER, SALES, DEVELOPER, INTERN
}

enum Department {
    ADMIN, TECH, FINANCE, SUPPORT, ENGINEERING, HR, SALES, MARKETING
}

public class Employee {
    private String firstName;
    private String lastName;
    private Role role;
    private Department department;
    private String reportingTo;
    private String id = "";

    private static int counter = 2; // acts like Kotlin's companion object

    public Employee(String firstName, String lastName, Role role, Department department, String reportingTo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
        this.reportingTo = reportingTo;
    }

    public boolean validate() {
        boolean isValid = !firstName.isBlank() &&
                          !lastName.isBlank() &&
                          (reportingTo.equals("0") || reportingTo.matches("^E\\d{3}$"));

        if (isValid && id.isEmpty()) {
            id = "E" + String.format("%03d", counter++);
        }
        return isValid;
    }

    public String getValidationError() {
        if (firstName.isBlank()) return "First name cannot be blank";
        if (lastName.isBlank()) return "Last name cannot be blank";
        if (!reportingTo.equals("0") && !reportingTo.matches("^E\\d{3}$")) {
            return "ReportingTo must be 0 or a valid employee ID (E001 format)";
        }
        return "";
    }

    @Override
    public String toString() {
        return "ID: " + id +
               "  Name: " + firstName + " " + lastName +
               "  Role: " + role +
               "  Dept: " + department +
               "  Reports To: " + reportingTo;
    }

    // Getters (if you need them later)
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Role getRole() { return role; }
    public Department getDepartment() { return department; }
    public String getReportingTo() { return reportingTo; }
}