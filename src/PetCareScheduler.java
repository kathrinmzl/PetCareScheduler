import java.util.*;
import java.time.LocalDateTime;

public class PetCareScheduler {
    // Instatiante the Scanner object for user input
    private static Scanner scanner = new Scanner(System.in);

    // Create a HashMap to store pet objects with petID as the key
    private static Map<String, Pet> petMap = new HashMap<>();

    // Main Method
    public static void main(String[] args) {
        // Load any existing pet data from file (if implemented)
        // loadPetData();

        boolean running = true;
        while (running) {
            System.out.println("Welcome to the Pet Care Scheduler!");
            System.out.println("1. Register A New Pet");
            System.out.println("2. Schedule Appointment For A Pet");
            System.out.println("3. Save Data");
            System.out.println("4. Display Details");
            System.out.println("5. Generate Report");
            System.out.println("6. Exit");
            System.out.print("Please select an option: ");
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
                    System.out.println("Exiting the Pet Care Scheduler. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

        // Methods to handle the choices
        private static void registerNewPet() {
            // Prompt the user for a unique pet ID
            System.out.print("Enter Pet ID: ");
            String petID = scanner.nextLine().trim();

            // Check if the pet ID already exists in the map
            if (petMap.containsKey(petID)) {
                System.out.println("Pet ID already exists. Please try again.");
                return;
            }

            // If the pet ID is unique, proceed to gather the rest of the pet details
            System.out.print("Enter Pet Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Species: ");
            String species = scanner.nextLine();
            System.out.print("Enter Age: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Owner Name: ");
            String ownerName = scanner.nextLine();
            System.out.print("Enter Owner Contact: ");
            String ownerContact = scanner.nextLine();

            // Create a new Pet object and add it to the petMap
            Pet newPet = new Pet(petID, name, species, age, ownerName, ownerContact);
            petMap.put(petID, newPet);
            System.out.println("Pet registered successfully on " + newPet.getRegistrationDate() + "!");
        }
        

        
    }
}
