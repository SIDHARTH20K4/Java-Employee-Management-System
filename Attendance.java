import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Attendance {
    private String employeeId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Duration workingHours;

    public Attendance(String employeeId, LocalDateTime checkIn) {
        this.employeeId = employeeId;
        this.checkIn = checkIn;
        this.checkOut = null;
        this.workingHours = null;
    }

    public String checkout(LocalDateTime time) {
        if (!time.isAfter(checkIn)) {
            return "Check-out cannot be before or same as check-in.";
        }
        this.checkOut = time;
        this.workingHours = Duration.between(checkIn, checkOut);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Checked out at " + time.format(formatter);
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String checkInTime = checkIn.format(dateFormatter);
        String checkOutTime = (checkOut != null) ? checkOut.format(timeFormatter) : "N/A";

        String hoursWorked;
        if (workingHours != null) {
            long hours = workingHours.toHours();
            long minutes = workingHours.toMinutes() % 60;
            hoursWorked = hours + "h " + minutes + "m";
        } else {
            hoursWorked = "N/A";
        }

        return "ID: " + employeeId +
               "  Check-in: " + checkInTime +
               "  Check-out: " + checkOutTime +
               "  Hours Worked: " + hoursWorked;
    }
}