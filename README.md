
# 🚗 Uber Java Simulation Project

A Java-based ride-sharing simulation mimicking core functionalities of Uber, including user registration, driver assignment, ride booking, and service management using object-oriented principles.

## 📌 Features

- 📍 Location mapping (cmap)
- 👤 User & driver profiles
- 🛺 Book rides with service selection
- 📦 Parcel/Delivery service option
- 🧮 Fare estimation logic
- 🎛️ CLI-based UI interface

## 📂 Project Structure

- `User.java` – Handles user attributes and interaction
- `drivers.java` – Maintains driver data and availability
- `Ride.java` – Manages ride booking and route logic
- `ServiceProvider.java` – Core backend class connecting services
- `services.java` – Defines various ride/delivery services
- `Delivery.java` – Parcel delivery handling class
- `cmap.java` – Custom map logic for route simulation
- `UI.java` – Text-based user interface logic

## 🔧 Technologies Used

- Java (OOP concepts)
- Command Line Interface (CLI)
- File-based or in-memory data (no DB used)

## 🚀 How to Run

1. Compile all `.java` files:
   ```bash
   javac *.java
