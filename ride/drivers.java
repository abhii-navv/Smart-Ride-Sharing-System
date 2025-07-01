package Uber.ride;
import java.util.*;

public class drivers implements ServiceProvider {
    private String driver_id;    // Now private
    private String name;
    private String carModel;
    private String licensePlate;
    private double wallet;
    public static enum Status { Available, Driving }; // Enum remains public for external use
    private Status status;

    // Constructor with validation
    public drivers(String driver_id, String name, String carModel, String licensePlate) {
        if (driver_id == null || driver_id.trim().isEmpty()) {
            throw new IllegalArgumentException("Driver ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (carModel == null || carModel.trim().isEmpty()) {
            throw new IllegalArgumentException("Car model cannot be null or empty");
        }
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }

        this.driver_id = driver_id.trim();
        this.name = name.trim();
        this.carModel = carModel.trim();
        this.licensePlate = licensePlate.trim();
        this.wallet = 0;          // Initialized to 0, no negative allowed
        this.status = Status.Available; // Default to Available
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String getId() {
        return driver_id;
    }

    // Setter with validation (optional, as IDs are typically immutable)
    public void setID(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Driver ID cannot be null or empty");
        }
        this.driver_id = id.trim();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        if (carModel == null || carModel.trim().isEmpty()) {
            throw new IllegalArgumentException("Car model cannot be null or empty");
        }
        this.carModel = carModel.trim();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        this.licensePlate = licensePlate.trim();
    }

    @Override
    public double getWallet() {
        return wallet;
    }

    @Override
    public void setWallet(double wallet) {
        if (wallet < 0) {
            throw new IllegalArgumentException("Wallet balance cannot be negative");
        }
        this.wallet = wallet;
    }

    // Private helper method to display info
    private void getInfo() {
        System.out.println("Driver ID: " + driver_id);
        System.out.println("Name: " + name);
        System.out.println("Car Model: " + carModel);
        System.out.println("License Plate: " + licensePlate);
        System.out.println("Wallet Balance: $" + wallet);
        System.out.println("Status: " + status);
        System.out.println("----------------------------------");
    }

    @Override
    public void displayInfo() {
        getInfo(); // Delegates to private method
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof drivers)) {
            return false;
        }
        drivers other = (drivers) obj;
        return this.name.equals(other.name) && this.licensePlate.equals(other.licensePlate);
    }

    // Controlled method to add payment to wallet
    public void pay(double fee) {
        if (fee < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative");
        }
        this.wallet += fee;
    }

    @Override
    public boolean isAvailable() {
        return status == Status.Available;
    }

    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status string cannot be null or empty");
        }
        String trimmedStatus = status.trim();
        if (trimmedStatus.equalsIgnoreCase("Available")) {
            this.status = Status.Available;
        } else if (trimmedStatus.equalsIgnoreCase("Driving")) {
            this.status = Status.Driving;
        } else {
            throw new IllegalArgumentException("Invalid status: must be 'Available' or 'Driving'");
        }
    }

    public static void main(String[] args) {
        try {
            drivers driver1 = new drivers("7001", "Tom Cruise", "Toyota Corolla", "ABC1234");
            drivers driver2 = new drivers("7002", "Brad Pitt", "Audi S4", "XYZ5678");
            drivers driver3 = new drivers("7003", "Tom Cruise", "Toyota Corolla", "ABC1234");

            System.out.println(driver1.getId());
            System.out.println(driver1.getLicensePlate());
            driver1.setCarModel("Toyota Innova");
            System.out.println(driver1.getCarModel());
            driver1.pay(50.0); // Controlled payment
            driver1.setStatus("Driving");
            driver1.displayInfo();
            System.out.println("Driver1 equals Driver3: " + driver1.equals(driver3));

            // Test invalid operation
            driver2.pay(-10.0); // Should throw exception
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}