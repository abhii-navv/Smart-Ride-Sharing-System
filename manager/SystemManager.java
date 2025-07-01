package Uber.manager;
import Uber.ride.*;
import java.util.*;

public class SystemManager {
    // Making this package-private (not private) for UI accessibility
    ArrayList<ServiceProvider> serviceProviders;
    ArrayList<User> users;
    private ArrayList<drivers> Drivers;
    private ArrayList<services> Services;
    private double totalRevenue;
    private double perUnitDelivery = 1.2;
    private double perUnitRides = 1.5;
    private double payRate = 0.1;

    private String errMessage = null;
    private RegistrationManager registrationManager;
    private int lastRequestId = 0;
    
    public SystemManager() {
        this.serviceProviders = new ArrayList<ServiceProvider>();
        this.users = new ArrayList<User>();
        this.Drivers = new ArrayList<drivers>();
        this.Services = new ArrayList<services>();
        this.registrationManager = new RegistrationManager(users, Drivers);
        registrationManager.loadPredefinedUsers();
        registrationManager.loadPredefinedDrivers();
        
        // Add all users and drivers to the serviceProviders list
        for (User user : users) {
            serviceProviders.add(user);
        }
        for (drivers driver : Drivers) {
            serviceProviders.add(driver);
        }
        
        this.totalRevenue = 0;
    }
    
    public String getErrorMessage() {
        return errMessage;
    }
    
    public ServiceProvider getServiceProvider(String id) {
        for (ServiceProvider provider : serviceProviders) {
            if (provider.getId().equals(id)) {
                return provider;
            }
        }
        return null;
    }
    
    public User getUser(String accountId) {
        for (User user : users) {
            if (user.getId().equals(accountId)) {
                return user;
            }
        }
        return null;
    }
    
    // New method to find user by name and address
    public User getUser(String name, String address) {
        for (User user : users) {
            if (user.getName().equals(name) && user.getAddress().equals(address)) {
                return user;
            }
        }
        return null;
    }
    
    private boolean userExists(User user) {
        for (User existingUser : users) {
            if (existingUser.equals(user)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean existingRequest(User user, String restaurant, String foodOrderId) {
        for (services service : Services) {
            if (service instanceof Delivery) {
                Delivery delivery = (Delivery) service;
                if (delivery.getUser().equals(user) &&
                    delivery.getRestaurant().equals(restaurant) &&
                    delivery.getFoodOrderID().equals(foodOrderId)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public drivers getDriver(String accountId) {
        for (drivers driver : Drivers) {
            if (driver.getId().equals(accountId)) {
                return driver;
            }
        }
        return null;
    }
    
    private boolean driverExists(drivers driver) {
        for (drivers existingDriver : Drivers) {
            if (existingDriver.equals(driver)) {
                return true;
            }
        }
        return false;
    }
    
    public double getDeliveryCost(int dist) {
        return perUnitDelivery * dist;
    }
    
    public double getRideCost(int dist) {
        return perUnitRides * dist;
    }
    
    public drivers getAvailableDriver() {
        for (ServiceProvider provider : serviceProviders) {
            if (provider instanceof drivers && provider.isAvailable()) {
                return (drivers) provider;
            }
        }
        return null;
    }
    
    public void listAllServiceProviders() {
        System.out.println("---- List of Service Providers ----");
        for (ServiceProvider provider : serviceProviders) {
            provider.displayInfo();
            System.out.println("----------------------");
        }
    }
    
    public void listAllUsers() {
        System.out.println("---- List of Users ----");
        for (User user : users) {
            user.displayInfo();
            System.out.println("----------------------");
        }
    }
    
    public void listAllDrivers() {
        System.out.println("---- List of Drivers ----");
        for (drivers driver : Drivers) {
            driver.displayInfo();
            System.out.println("----------------------");
        }
    }
    
    public void listAllServices() {
        System.out.println("---- List of Services ----");
        int index = 1;
        for (services service : Services) {
            System.out.println("Request #" + index);
            service.PrintInfo();
            System.out.println("----------------------");
            index++;
        }
    }
    
    public boolean cancelReq(int req) {
        int adjReq = req - 1;
        if (adjReq < 0 || adjReq >= Services.size()) {
            errMessage = "invalid request";
            return false;
        }
        services temp = Services.remove(adjReq);
        temp.getDriver().setStatus(drivers.Status.Available);
        return true;
    }
    
    public boolean registerUser(String name, String address, Double wallet) {
        if (name == null || name.isEmpty() ||
            address == null || address.isEmpty() ||
            wallet == null || wallet < 0) {
            errMessage = "Invalid user";
            return false;
        }

        String userId = registrationManager.generateUserId();
        User tempUser = new User(userId, name, address, wallet);
        if (userExists(tempUser)) {
            errMessage = "User already exists";
            return false;
        }
        users.add(tempUser);
        serviceProviders.add(tempUser);
        System.out.println("User registered successfully:");
        return true;
    }
    
    public boolean registerNewDriver(String name, String carModel, String carLicencePlate) {
        if (name == null || name.isEmpty()) {
            errMessage = "Invalid Driver Name";
            return false;
        }
        if (carModel == null || carModel.isEmpty()) {
            errMessage = "Invalid Car Model";
            return false;
        }
        if (carLicencePlate == null || carLicencePlate.isEmpty()) {
            errMessage = "Invalid Car Licence Plate";
            return false;
        }

        String driverId = registrationManager.generateDriverId();
        drivers newDriver = new drivers(driverId, name, carModel, carLicencePlate);
        if (driverExists(newDriver)) {
            errMessage = "Driver Already Exists in System";
            return false;
        }
        Drivers.add(newDriver);
        serviceProviders.add(newDriver);
        return true;
    }
    
    public String getUsernameFromId(String accountId) {
        ServiceProvider provider = getServiceProvider(accountId);
        if (provider != null) {
            return provider.getName();
        }
        return null;
    }
    
    public boolean existingRequest(services req) {
        for (services service : Services) {
            if (service.equals(req)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean requestRide(String accountId, String from, String to) {
        if (accountId == null || accountId.isEmpty()) {
            errMessage = "Invalid User Account";
            return false;
        }
        if (from == null || from.isEmpty() || to == null || to.isEmpty()) {
            errMessage = "Invalid Address";
            return false;
        }
        User user = getUser(accountId);
        if (user == null) {
            errMessage = "User Account Not Found";
            return false;
        }
        
        int distance = cmap.distance(from, to);
        if (distance == -1) {
            errMessage = "Invalid Address Format. Please use one of the following formats:\n" +
                         "- Landmarks: Downtown, Airport, Station, Terminal, City Center, Tech Park, University, Central Mall, Old Town\n" +
                         "- Street format: '5 3rd Street' or '7 2nd Avenue'";
            return false;
        }
        if (distance <= 1) {
            errMessage = "Insufficient Travel Distance";
            return false;
        }
        
        double ridecost = getRideCost(distance);
        if (user.getWallet() < ridecost) {
            errMessage = "Insufficient Funds";
            return false;
        }
        drivers driver = getAvailableDriver();
        if (driver == null) {
            errMessage = "No Drivers Available";
            return false;
        }
        Ride ride = new Ride(driver, from, to, user, distance, getRideCost(distance));
        if (existingRequest(ride)) {
            errMessage = "User Already Has Ride Request";
            return false;
        }
        driver.setStatus(drivers.Status.Driving);
        Services.add(ride);
        lastRequestId++;
        return true;
    }
    
    public boolean requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId) {
        if (accountId == null || accountId.isEmpty()) {
            errMessage = "Invalid User Account";
            return false;
        }
        if (from == null || from.isEmpty() || to == null || to.isEmpty()) {
            errMessage = "Invalid Address";
            return false;
        }
        if (restaurant == null || restaurant.isEmpty() || foodOrderId == null || foodOrderId.isEmpty()) {
            errMessage = "Invalid Restaurant or Food Order";
            return false;
        }
        User user = getUser(accountId);
        if (user == null) {
            errMessage = "User Account Not Found";
            return false;
        }
        
        int distance = cmap.distance(from, to);
        if (distance == -1) {
            errMessage = "Invalid Address Format. Please use one of the following formats:\n" +
                         "- Landmarks: Downtown, Airport, Station, Terminal, City Center, Tech Park, University, Central Mall, Old Town\n" +
                         "- Street format: '5 3rd Street' or '7 2nd Avenue'";
            return false;
        }
        
        double deliveryCost = getDeliveryCost(distance);
        if (user.getWallet() < deliveryCost) {
            errMessage = "Insufficient Funds";
            return false;
        }
        drivers driver = getAvailableDriver();
        if (driver == null) {
            errMessage = "No Drivers Available";
            return false;
        }
        Delivery delivery = new Delivery(driver, from, to, user, distance, deliveryCost, restaurant, foodOrderId);
        if (existingRequest(user, restaurant, foodOrderId)) {
            errMessage = "User Already Has Delivery Request at Restaurant with this Food Order";
            return false;
        }
        driver.setStatus(drivers.Status.Driving);
        Services.add(delivery);
        lastRequestId++;
        return true;
    }
    
    public boolean dropOff(int req) {
        int adjReq = req - 1;
        if (adjReq < 0 || adjReq >= Services.size()) {
            errMessage = "invalid req";
            return false;
        }
        services temp = Services.get(adjReq);
        double cost = temp.getCost();
        
        // Deduct the full cost from user's wallet
        temp.getUser().deductWallet(cost);
        
        // Add the full cost to total revenue
        totalRevenue += cost;
        
        // Calculate and pay driver's commission
        double driverFee = cost * payRate;
        temp.getDriver().setWallet(temp.getDriver().getWallet() + driverFee);
        
        // Adjust total revenue by removing driver's payment
        totalRevenue -= driverFee;
        
        // Set driver back to available
        temp.getDriver().setStatus(drivers.Status.Available);

        // Remove the service after completing it
        Services.remove(adjReq);
        return true;
    }
    
    public String getLastRequestId() {
        return "REQ" + lastRequestId;
    }
}