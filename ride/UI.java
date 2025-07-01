package Uber.ride;
import Uber.manager.*;
import java.util.Scanner;

public class UI {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SystemManager obj = new SystemManager();

        // Display available commands when starting the program
        displayCommands();
        System.out.println(">");

        while (sc.hasNextLine()) { 
            String action = sc.nextLine();

            if (action.isEmpty()) {
                System.out.println("\n");
                continue;
            }

            switch (action.toLowerCase()) {
                case "q":
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                    
                case "drivers":
                    obj.listAllDrivers();
                    break;
                    
                case "requests":
                    obj.listAllServices();
                    break;
                    
                case "register drivers":
                    System.out.println("Name:");
                    String name = sc.nextLine();

                    System.out.println("Car Model:");
                    String carModel = sc.nextLine();

                    System.out.println("License Plate:");
                    String licensePlate = sc.nextLine();

                    if (!obj.registerNewDriver(name, carModel, licensePlate)) {
                        System.out.println(obj.getErrorMessage());
                    } else {
                        System.out.println("Saved Successfully!");
                    }
                    break;
                    
                case "register user":
                    System.out.println("Name:");
                    String userName = sc.nextLine();

                    System.out.println("Address:");
                    String address = sc.nextLine();
                    
                    System.out.println("Wallet:");
                    double wallet = 0;
                    try {
                        wallet = Double.parseDouble(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid wallet amount. Please enter a valid number.");
                        break;
                    }

                    if (!obj.registerUser(userName, address, wallet)) {
                        System.out.println(obj.getErrorMessage());
                    } else {
                        System.out.println("User Registered Successfully!");
                        // Get the user ID from the last registered user
                        User lastUser = obj.getUser(userName, address);
                        if (lastUser != null) {
                            System.out.println("User ID: " + lastUser.getId());
                        }
                    }
                    break;
                    
                case "request ride":
                    System.out.print("User ID: ");
                    String userId = sc.nextLine();

                    System.out.print("Pickup Location: ");
                    String pickup = sc.nextLine();

                    System.out.print("Drop-off Location: ");
                    String dropOff = sc.nextLine();

                    if (!obj.requestRide(userId, pickup, dropOff)) {
                        System.out.println(obj.getErrorMessage());
                    } else {
                        System.out.println("Ride Requested Successfully!");
                        System.out.println("Request ID: " + obj.getLastRequestId());
                    }
                    break;
                    
                case "request delivery":
                    System.out.print("User ID: ");
                    String deliveryUserId = sc.nextLine();

                    System.out.print("Pickup Location: ");
                    String from = sc.nextLine();

                    System.out.print("Destination: ");
                    String to = sc.nextLine();

                    System.out.print("Restaurant: ");
                    String restaurant = sc.nextLine();

                    System.out.print("Food Order ID: ");
                    String foodOrderId = sc.nextLine();

                    if (!obj.requestDelivery(deliveryUserId, from, to, restaurant, foodOrderId)) {
                        System.out.println(obj.getErrorMessage());
                    } else {
                        System.out.println("Delivery Requested Successfully!");
                        System.out.println("Request ID: " + obj.getLastRequestId());
                    }
                    break;
                    
                case "cancel request":
                    System.out.println("Available requests:");
                    obj.listAllServices();
                    
                    System.out.print("Request Number: ");
                    int cancelReqId = 0;
                    try {
                        cancelReqId = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid request number. Please enter a valid integer.");
                        break;
                    }

                    if (!obj.cancelReq(cancelReqId)) {
                        System.out.println(obj.getErrorMessage());
                    } else {
                        System.out.println("Request Cancelled Successfully!");
                    }
                    break;
                    
                case "drop off":
                    System.out.println("Available requests:");
                    obj.listAllServices();
                    
                    System.out.print("Request Number: ");
                    int dropOffReqId = 0;
                    try {
                        dropOffReqId = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid request number. Please enter a valid integer.");
                        break;
                    }

                    if (!obj.dropOff(dropOffReqId)) {
                        System.out.println(obj.getErrorMessage());
                    } else {
                        System.out.println("Drop-Off Completed Successfully!");
                    }
                    break;
                    
                case "help":
                    displayCommands();
                    break;
                    
                default:
                    System.out.println("Invalid command. Try again.");
                    displayCommands();
                    break;
            }
            
            System.out.println(">");
        }
    }
    
    /**
     * Displays all available commands to the user
     */
    private static void displayCommands() {
        System.out.println("\n===== AVAILABLE COMMANDS =====");
        System.out.println("Drivers - List all registered drivers");
        System.out.println("Requests - List all service requests");
        System.out.println("Register Drivers - Register a new driver");
        System.out.println("Register User - Register a new user");
        System.out.println("Request Ride - Request a ride service");
        System.out.println("Request Delivery - Request a food delivery service");
        System.out.println("Cancel Request - Cancel an existing request");
        System.out.println("Drop Off - Complete a service with drop-off");
        System.out.println("Help - Display this help message");
        System.out.println("Q - Quit the program");
        System.out.println("============================");
    }
}