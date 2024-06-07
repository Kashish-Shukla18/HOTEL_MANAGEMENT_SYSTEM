# Hotel Reservation System

## Overview
The Hotel Reservation System is a Java application designed to manage hotel reservations. It connects to a MySQL database and provides functionalities such as reserving a room, viewing reservations, retrieving room numbers, updating reservations, and deleting reservations.

## Features
- **Reserve a Room**: Add a new reservation with guest details.
- **View Reservations**: Display all existing reservations.
- **Get Room Number**: Retrieve the room number for a specific reservation.
- **Update Reservation**: Modify details of an existing reservation.
- **Delete Reservation**: Remove an existing reservation.

## Requirements
- Java Development Kit (JDK) 8 or later
- MySQL database server
- MySQL JDBC Driver

## Setup

### Database
1. Install and start MySQL server.
2. Create a database named `hotel_db`.
3. Create a table named `reservations` with the following schema:
    ```sql
    CREATE TABLE reservations (
        reservation_id INT AUTO_INCREMENT PRIMARY KEY,
        guest_name VARCHAR(100) NOT NULL,
        room_number INT NOT NULL,
        contact_number VARCHAR(15) NOT NULL,
        reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
    ```

### Java Application
1. Clone or download the project files.
2. Open the project in your preferred IDE.
3. Ensure the MySQL JDBC Driver is included in your project dependencies. If not, download the driver from the [MySQL website](https://dev.mysql.com/downloads/connector/j/) and add it to your project.

## Configuration
Update the database connection details in the `HotelReservationSystem` class:
```java
private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
private static final String username = "root";
private static final String password = "********";  // Replace with your MySQL password
