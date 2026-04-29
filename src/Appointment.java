import java.io.Serializable;
import java.time.LocalDateTime;

public class Appointment implements Serializable {
    private String appointmentType;
    private LocalDateTime appointmentDateTime;
    private String notes;

    // Full constructor
    public Appointment(String appointmentType, LocalDateTime appointmentDateTime, String notes) {
        this.appointmentType = appointmentType;
        this.appointmentDateTime = appointmentDateTime;
        this.notes = notes;
    }

    // Constructor without notes
    public Appointment(String appointmentType, LocalDateTime appointmentDateTime) {
        this(appointmentType, appointmentDateTime, "");
    }

    // Getters and Setters
    public String getAppointmentType() {
        return appointmentType;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public String getNotes() {
        return notes;
    }

    // public void setAppointmentType(String appointmentType) {
    //     this.appointmentType = appointmentType;
    // }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // toString method for easy display
    @Override   
    public String toString() {
        return "Appointment Details:\n" +
            "Type = " + appointmentType + "\n" +
            "DateTime = " + appointmentDateTime + "\n" +
            "Notes = " + notes + "\n";
    }
    
}
