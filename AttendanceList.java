import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AttendanceList {
    private String employeeId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Duration workingHours;

    public AttendanceList(String employeeId, LocalDateTime checkIn) {
        this.employeeId = employeeId;
        this.checkIn = checkIn;
    }

    public String checkout(LocalDateTime time) {
        if (!time.isAfter(checkIn)) {
            return "Check-out cannot be before or same as check-in.";
        }
        this.checkOut = time;
        this.workingHours = Duration.between(checkIn, checkOut);
        return "Checked out at " + time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // ✅ Getters
    public String getEmployeeId() {
        return employeeId;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public Duration getWorkingHours() {
        return workingHours;
    }

    @Override
    public String toString() {
        String checkInTime = checkIn.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String checkOutTime = (checkOut != null) ? checkOut.format(DateTimeFormatter.ofPattern("HH:mm")) : "N/A";
        String hoursWorked = (workingHours != null) ? workingHours.toHours() + "h " + workingHours.toMinutesPart() + "m" : "N/A";
        return "ID: " + employeeId + "  Check-in: " + checkInTime + "  Check-out: " + checkOutTime + "  Hours Worked: " + hoursWorked;
    }
}
