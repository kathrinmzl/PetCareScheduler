import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pet implements Serializable{
    private String petID;
    private String name;
    private String species;
    private int age;
    private String ownerName;
    private String ownerContact;
    private LocalDate registrationDate;
    private List<Appointment> appointments;

    // Full constructor
    public Pet(String petID, String name, String species, int age, String ownerName, String ownerContact) {
        this.petID = petID;
        this.name = name;
        this.species = species;
        this.age = age;
        this.ownerName = ownerName;
        this.ownerContact = ownerContact;
        this.registrationDate = LocalDate.now();
        this.appointments = new ArrayList<>();
    }

    // Getters and Setters
    public String getPetID() { return petID; }
    public String getName() { return name; }
    public String getSpecies() { return species; }
    public int getAge() { return age; }
    public String getOwnerName() { return ownerName; }
    public String getOwnerContact() { return ownerContact; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public List<Appointment> getAppointments() { return appointments; }

    public void setOwnerContact(String ownerContact) { this.ownerContact = ownerContact; }
    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }
}
