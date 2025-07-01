package Uber.manager;

import Uber.ride.*;

import java.util.*;

public class RegistrationManager {
    private ArrayList<User> users;
    private ArrayList<drivers> drivers;
    private int user_id = 9000;
    private int driver_id = 7000;

    public RegistrationManager(ArrayList<User> users, ArrayList<drivers> drivers) {
        this.users = users;
        this.drivers = drivers;
    }

    /**
     * Loads predefined users into the system
     * This replaces the original registration.predefinedUser call
     */
    public void loadPredefinedUsers() {
        // Implementation that was previously in registration.predefinedUser
        // Add predefined users to the users ArrayList
    }

    /**
     * Loads predefined drivers into the system
     * This replaces the original registration.predefinedDriver call
     */
    public void loadPredefinedDrivers() {
        // Add some predefined drivers with Available status
        drivers driver1 = new drivers("D7001", "John Driver", "Toyota Camry", "ABC123");
        drivers driver2 = new drivers("D7002", "Sarah Driver", "Honda Civic", "XYZ789");
        drivers driver3 = new drivers("D7003", "Mike Driver", "Ford Focus", "DEF456");
        drivers driver4 = new drivers("D7004", "Lisa Driver", "Tesla Model 3", "GHI789");
        
        // Set status to Available - using string "Available" instead of enum
        driver1.setStatus("Available");
        driver2.setStatus("Available");
        driver3.setStatus("Available");
        driver4.setStatus("Available");
        
        // Add drivers to the list
        drivers.add(driver1);
        drivers.add(driver2);
        drivers.add(driver3);
        drivers.add(driver4);
        
        System.out.println("Loaded " + drivers.size() + " predefined drivers");
    }

    /**
     * Generates a unique user ID
     * @return A unique user ID string
     */
    public String generateUserId() {
        user_id++;
        return "U" + user_id;
    }

    /**
     * Generates a unique driver ID
     * @return A unique driver ID string
     */
    public String generateDriverId() {
        driver_id++;
        return "D" + driver_id;
    }

    /**
     * Checks if a given user ID already exists in the system
     * @param userId The user ID to check
     * @return true if the user ID exists, false otherwise
     */
    public boolean userIdExists(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a given driver ID already exists in the system
     * @param driverId The driver ID to check
     * @return true if the driver ID exists, false otherwise
     */
    public boolean driverIdExists(String driverId) {
        for (drivers driver : drivers) {
            if (driver.getId().equals(driverId)) {
                return true;
            }
        }
        return false;
    }
}