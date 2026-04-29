import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PetCareScheduler {
    // Instatiante the Scanner object for user input
    private static Scanner scanner = new Scanner(System.in);

    // Create a HashMap to store pet objects with petID as the key
    private static Map<String, Pet> petMap = new HashMap<>();

    // Main Method
    public static void main(String[] args) {
        // Load any existing pet data from file (if implemented)
        loadPetData();

        boolean running = true;
        while (running) {
            System.out.println("===== Welcome to the Pet Care Scheduler! =====");
            System.out.println("1. Register A New Pet");
            System.out.println("2. Schedule Appointment For A Pet");
            System.out.println("3. Save Data");
            System.out.println("4. Display Details");
            System.out.println("5. Generate Report");
            System.out.println("6. Exit");
            System.out.println("Please select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerNewPet();
                    break;
                case "2":
                    scheduleAppointment();
                    break;
                case "3":
                    saveData();
                    break;
                case "4":
                    displayDetails();
                    break;
                case "5":
                    generateReport();
                    break;
                case "6":
                    running = false;
                    System.out.println("===== Exiting the Pet Care Scheduler. Goodbye! =====");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

        // Methods to handle the choices
        private static void registerNewPet() {
            // Prompt the user for a unique pet ID
            System.out.println("Enter Pet ID: ");
            String petID = scanner.nextLine().trim();

            // Check if the pet ID already exists in the map
            if (petMap.containsKey(petID)) {
                System.out.println("Pet ID already exists. Please try again.");
                return;
            }

            // If the pet ID is unique, proceed to gather the rest of the pet details
            System.out.println("Enter Pet Name: ");
            String name = scanner.nextLine();
            System.out.println("Enter Species: ");
            String species = scanner.nextLine();
            System.out.println("Enter Age: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter Owner Name: ");
            String ownerName = scanner.nextLine();
            System.out.println("Enter Owner Contact: ");
            String ownerContact = scanner.nextLine();

            // Create a new Pet object and add it to the petMap
            Pet newPet = new Pet(petID, name, species, age, ownerName, ownerContact);
            petMap.put(petID, newPet);
            System.out.println("Pet registered successfully on " + newPet.getRegistrationDate() + "!");
            System.out.println("\n");
        }
        
        private static void scheduleAppointment() {
            // Prompt the user for the pet ID
            System.out.println("Enter Pet ID: ");
            String petID = scanner.nextLine().trim();

            // Look up the pet in the petMap by petID
            Pet pet = petMap.get(petID);
            if (pet == null) {
                System.out.println("Error: Pet ID not found. Please try again.");
                return;
            }

            // If the pet is found, prompt the user for appointment details
            // AppointmentType with validation
            List<String> validTypes = Arrays.asList("vet visit", "grooming", "vaccination", "checkup");

            String appointmentType;
            while (true) {
                System.out.println("Enter Appointment Type (Vet Visit, Grooming, Vaccination or Checkup): ");
                appointmentType = scanner.nextLine().trim().toLowerCase();

                if (validTypes.contains(appointmentType)) {
                    break;
                } else {
                    System.out.println("Invalid type. Choose from: vet visit, grooming, vaccination, checkup. Try again.");
                }
            }

            // AppointmentDateTime with validation
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
            LocalDateTime appointmentDateTime = null;

            while (true) { // Loop until a valid date and time is entered
                System.out.println("Enter Appointment Date and Time (dd.MM.yyyy, HH:mm): ");
                String dateTimeInput = scanner.nextLine(); 

                try {
                    // Parse the input string into a LocalDateTime object
                    appointmentDateTime = LocalDateTime.parse(dateTimeInput, formatter);

                    // Check that date is in the future
                    if (appointmentDateTime.isBefore(LocalDateTime.now())) {
                        throw new IllegalArgumentException();
                    }

                    break; // valid input, exit loop

                } catch (DateTimeParseException e) {
                    System.out.println("Invalid format. Please use \"dd.MM.yyyy, HH:mm\".");

                } catch (IllegalArgumentException e) {
                    System.out.println("Appointment must be in the future. Try again.");
                }
            }

            // Notes (optional)
            System.out.println("Enter any notes for the appointment (optional): ");
            String notes = scanner.nextLine();  

            // Create a new Appointment object and add it to the pet's appointments list
            Appointment newAppointment = new Appointment(appointmentType, appointmentDateTime, notes);
            pet.addAppointment(newAppointment);
            
            // Success Message
            System.out.println("Appointment scheduled successfully for " + pet.getName() + "!");

            System.out.println("\n");
        }
        
        private static void saveData() {
            try {
                // Create a FileOutputStream to write to the file named "pets.ser"
                ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("pets.ser")
                );
                // Write the entire pet map to the file
                out.writeObject(petMap);

                // Success Message
                System.out.println("Data saved successfully.");
            } catch (IOException e) {
                // If something goes wrong while saving, print an error message
                System.out.println("Error saving data: " + e.getMessage());
            }

            System.out.println("\n");
            
        }
        
        private static void displayDetails() {
            // Prompt the user to choose which details to display
            System.out.println("Choose one of the following options to display details:");
            System.out.println("1. List of All Registered Pets");
            System.out.println("2. Appointments for a Specific Pet");
            System.out.println("3. Upcoming Appointments for all pets");
            System.out.println("4. Past Appointments for all pets");
            System.out.println("5. Back to Main Menu");
            System.out.println("Please select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Registered Pets:");
                    // Loop through the petMap and print details of each pet
                    for (Pet pet : petMap.values()) {
                        System.out.println("Pet ID: " + pet.getPetID());
                        System.out.println("Name: " + pet.getName());
                        System.out.println("Species: " + pet.getSpecies());
                        System.out.println("Age: " + pet.getAge());
                        System.out.println("Owner Name: " + pet.getOwnerName());
                        System.out.println("Owner Contact: " + pet.getOwnerContact());
                        System.out.println("Registration Date: " + pet.getRegistrationDate());
                        System.out.println("-----------------------------");
                    }
                    break;
                case "2":
                    System.out.println("Enter Pet ID to view appointments: ");
                    String petID = scanner.nextLine().trim();
                    Pet pet = petMap.get(petID);
                    if (pet != null) {
                        System.out.println("Appointments for " + pet.getName() + ":");

                        System.out.println("Upcoming Appointments:");
                        for (Appointment appointment : pet.getAppointments()) {
                            if (appointment.getAppointmentDateTime().isAfter(LocalDateTime.now()) || 
                            appointment.getAppointmentDateTime().isEqual(LocalDateTime.now())) {
                                System.out.println(appointment);
                            }
                        }

                        System.out.println("Past Appointments:");
                        for (Appointment appointment : pet.getAppointments()) {
                            if (appointment.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
                                System.out.println(appointment);
                            }
                        }
                    } else {
                        System.out.println("Pet ID not found. Please try again.");
                    }

                    break;
                case "3":
                    for (Pet p : petMap.values()) {
                        System.out.println("Upcoming Appointments for " + p.getName() + ":");
                        for (Appointment appointment : p.getAppointments()) {
                            if (appointment.getAppointmentDateTime().isAfter(LocalDateTime.now()) || 
                            appointment.getAppointmentDateTime().isEqual(LocalDateTime.now())) {
                                System.out.println(appointment);
                            }
                        }
                        System.out.println("-----------------------------");
                    }
                    break;
                case "4":
                    for (Pet p : petMap.values()) {
                        System.out.println("Past Appointments for " + p.getName() + ":");
                        for (Appointment appointment : p.getAppointments()) {
                            if (appointment.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
                                System.out.println(appointment);
                            }
                        }
                        System.out.println("-----------------------------");
                    }
                    break;
                case "5":
                    return; // Go back to main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println("\n");
        }
    
        private static void generateReport() {
            LocalDateTime currentDateTime = LocalDateTime.now();
            System.out.println("1. Pets with upcoming appointments in the next week");
            LocalDateTime oneWeekLater = currentDateTime.plusWeeks(1);

            for( Pet pet : petMap.values()) {
                // Print a pet's details if the pet has any appointments in the next week
                for (Appointment appointment : pet.getAppointments()) {
                    LocalDateTime appointmentDateTime = appointment.getAppointmentDateTime();
                    if (appointmentDateTime.isAfter(currentDateTime) && 
                    appointmentDateTime.isBefore(oneWeekLater)) {
                        System.out.println("Pet ID: " + pet.getPetID());
                        System.out.println("Name: " + pet.getName());
                        System.out.println("Owner Name: " + pet.getOwnerName());
                        System.out.println("Owner Contact: " + pet.getOwnerContact());
                        System.out.println("-----------------------------");
                        break; // avoid printing same pet multiple times
                    }
                }
            }

            System.out.println("2. Pets with no visit in the last 6 months");
            LocalDateTime sixMonthsAgo = currentDateTime.minusMonths(6);

            for ( Pet pet : petMap.values()) {
                // Check if the pet has any appointments in the last 6 months
                boolean hasRecentAppointment = false;
                for (Appointment appointment : pet.getAppointments()) { 
                    LocalDateTime appointmentDateTime = appointment.getAppointmentDateTime();
                    if (appointmentDateTime.isAfter(sixMonthsAgo)) {
                        hasRecentAppointment = true;
                        break; // no need to check further appointments for this pet
                    }
                }

                // If the pet has no appointments in the last 6 months, print their details
                if (!hasRecentAppointment) {
                    System.out.println("Pet ID: " + pet.getPetID());
                    System.out.println("Name: " + pet.getName());
                    System.out.println("Owner Name: " + pet.getOwnerName());
                    System.out.println("Owner Contact: " + pet.getOwnerContact());
                    System.out.println("-----------------------------");
                }
            }
            System.out.println("\n");
        }

        @SuppressWarnings("unchecked") // Suppresses unchecked cast warning when reading the object
        private static void loadPetData() {
            // Use a try-with-resources block to automatically close the input stream
            try (
                // Open an ObjectInputStream to read from the file "pets.ser"
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("pets.ser"))
            ) {
                // Read the object from the file and cast it back to the correct type
                petMap = (Map<String, Pet>) in.readObject();

                // Confirmation message to let the user know data was loaded
                System.out.println("Pet data loaded.");
            } catch (FileNotFoundException e) {
                // If the file doesn't exist yet, that's okay — start with empty data
                System.out.println("No saved data found. Starting fresh.");
            } catch (IOException | ClassNotFoundException e) {
                // Handle other errors, like if the file is corrupted or unreadable
                System.out.println("Error loading data: " + e.getMessage());
            }
            System.out.println("\n");
        }
    }

