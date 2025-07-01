package Uber.ride;
import java.util.*;

public class User implements ServiceProvider {
    private String user_id;    // Now private
    private String name;
    private String address;
    private double wallet;
    private int rides;
    private int delivery;

    // Constructor with validation
    public User(String user_id, String name, String address, double wallet) {
        if (user_id == null || user_id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        if (wallet < 0) {
            throw new IllegalArgumentException("Wallet balance cannot be negative");
        }

        this.user_id = user_id.trim();
        this.name = name.trim();
        this.address = address.trim();
        this.wallet = wallet;
        this.rides = 0;       // Initialized to 0, no negative allowed
        this.delivery = 0;    // Initialized to 0, no negative allowed
    }

    @Override
    public String getId() {
        return user_id;
    }

    // Setter with validation (optional, as IDs are typically immutable)
    public void setId(String user_id) {
        if (user_id == null || user_id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        this.user_id = user_id.trim();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        this.address = address.trim();
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

    public int getRides() {
        return rides;
    }

    public void setRides(int rides) {
        if (rides < 0) {
            throw new IllegalArgumentException("Number of rides cannot be negative");
        }
        this.rides = rides;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        if (delivery < 0) {
            throw new IllegalArgumentException("Number of deliveries cannot be negative");
        }
        this.delivery = delivery;
    }

    // Private helper method to display info (not directly accessible)
    private void infoUser() {
        System.out.println("User ID: " + user_id);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Wallet Balance: $" + wallet);
        System.out.println("Total Rides: " + rides);
        System.out.println("Total Deliveries: " + delivery);
    }

    @Override
    public void displayInfo() {
        infoUser(); // Delegates to private method
    }

    @Override
    public boolean isAvailable() {
        // Users are always available to receive services
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        return this.name.equals(other.name) && this.address.equals(other.address);
    }

    // Controlled wallet deduction with validation
    public void deductWallet(double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost cannot be negative");
        }
        if (wallet < cost) {
            throw new IllegalStateException("Insufficient funds in wallet");
        }
        this.wallet -= cost;
    }

    // Method to add funds to wallet (optional enhancement)
    public void addFunds(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to add cannot be negative");
        }
        this.wallet += amount;
    }

    public static void main(String[] args) {
        try {
            User user1 = new User("1001", "Alice Johnson", "123 Main St, Toronto", 55);
            User user2 = new User("1002", "Bob Smith", "456 Elm St, Vancouver", 150);

            user1.deductWallet(5.0);  // Controlled deduction
            user1.setRides(5);
            user1.setDelivery(2);

            user2.addFunds(20.0);     // Controlled addition
            user2.setRides(3);
            user2.setDelivery(1);

            user1.displayInfo();
            System.out.println();
            user2.displayInfo();

            // Test invalid operation
            user1.deductWallet(100.0); // Should throw exception
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}