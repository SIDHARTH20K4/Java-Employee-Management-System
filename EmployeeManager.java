public class EmployeeManager {
    private static int counter = 1;
    private String id;
    private String firstName;
    private String lastName;
    private Role role;
    private Department department;
    private String reportingTo;
    private String validationError;

    public EmployeeManager(String firstName, String lastName, Role role, Department department, String reportingTo) {
        this.id = "E" + String.format("%03d", counter++);
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
        this.reportingTo = reportingTo;
    }

    // ✅ Validation
    public boolean validate() {
        if (firstName == null || firstName.isBlank()) {
            validationError = "First name cannot be empty.";
            return false;
        }
        if (lastName == null || lastName.isBlank()) {
            validationError = "Last name cannot be empty.";
            return false;
        }
        if (reportingTo == null || reportingTo.isBlank()) {
            validationError = "ReportingTo cannot be empty.";
            return false;
        }
        validationError = null;
        return true;
    }

    public String getValidationError() {
        return validationError != null ? validationError : "";
    }

    // ✅ Getters + Setter for ID
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Role getRole() { return role; }
    public Department getDepartment() { return department; }
    public String getReportingTo() { return reportingTo; }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Name: " + firstName + " " + lastName +
                " | Role: " + role +
                " | Department: " + department +
                " | Reports To: " + reportingTo;
    }
}
