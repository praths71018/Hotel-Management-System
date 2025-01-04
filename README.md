# Hotel Management System

![Java](https://img.shields.io/badge/Java-100%25-orange)
![MongoDB](https://img.shields.io/badge/Database-MongoDB-green)

## Overview

This project is a Hotel Management System built using Java Swing for the user interface and MongoDB for the database. The system allows users to manage various aspects of hotel operations including room bookings, customer information, and billing.

## Features

- **Room Booking**: Allows customers to book rooms, check availability, and manage reservations.
- **Customer Management**: Stores and manages customer information.
- **Billing System**: Generates bills for the customers based on their stay and services used.
- **User-friendly Interface**: Built using Java Swing for a responsive and interactive UI.
- **Database Management**: Uses MongoDB to store and manage data efficiently.

## Installation

### Prerequisites

- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (version 11 or higher)
- [MongoDB](https://www.mongodb.com/try/download/community) (version 4.4 or higher)

### Steps

1. **Clone the repository:**
    ```sh
    git clone https://github.com/praths71018/Hotel-Management-System.git
    cd Hotel-Management-System
    ```

2. **Set up MongoDB:**
   - Install MongoDB and start the MongoDB server.
   - Create a database named `hotel_management`.

3. **Configure the application:**
   - Update the MongoDB connection settings in the `config` package.

4. **Compile and run the application:**
    ```sh
    javac -d bin src/*.java
    java -cp bin Main
    ```

## Usage

1. **Start the Application:**
   - Run the main class to launch the application.
   - The main UI window will appear.

2. **Room Booking:**
   - Navigate to the booking section.
   - Select the room type and dates.
   - Enter customer details and confirm the booking.

3. **Manage Customers:**
   - Navigate to the customer management section.
   - Add, update, or delete customer information.

4. **Billing:**
   - Navigate to the billing section.
   - Generate bills for the customers based on their stay and services used.

## Design Patterns Used

1. **Singleton Pattern**:
   - **Used In**: Database Connection Manager
   - **Description**: Ensures a class has only one instance and provides a global point of access to it. For example, a MongoDB connection manager that ensures only one instance of the database connection is created.
   - **Code Example**:
     ```java
     public class MongoDBConnection {
         private static MongoDBConnection instance;
         private MongoClient client;
         
         private MongoDBConnection() {
             // Initialize MongoClient
         }

         public static MongoDBConnection getInstance() {
             if (instance == null) {
                 instance = new MongoDBConnection();
             }
             return instance;
         }

         public MongoClient getClient() {
             return client;
         }
     }
     ```

2. **Factory Pattern**:
   - **Used In**: Room Creation
   - **Description**: Defines an interface for creating an object, but lets subclasses alter the type of objects that will be created. For example, creating different types of rooms (Standard, Deluxe, Suite).
   - **Code Example**:
     ```java
     public abstract class RoomFactory {
         public abstract Room createRoom();
     }

     public class StandardRoomFactory extends RoomFactory {
         @Override
         public Room createRoom() {
             return new StandardRoom();
         }
     }

     public class DeluxeRoomFactory extends RoomFactory {
         @Override
         public Room createRoom() {
             return new DeluxeRoom();
         }
     }
     ```

3. **Iterator Pattern**:
   - **Used In**: Customer List Management
   - **Description**: Provides a way to access the elements of an aggregate object sequentially without exposing its underlying representation. For example, iterating over a list of customers.
   - **Code Example**:
     ```java
     public interface Iterator {
         boolean hasNext();
         Object next();
     }

     public class CustomerIterator implements Iterator {
         private List<Customer> customers;
         private int position;

         public CustomerIterator(List<Customer> customers) {
             this.customers = customers;
             this.position = 0;
         }

         @Override
         public boolean hasNext() {
             return position < customers.size();
         }

         @Override
         public Object next() {
             if (this.hasNext()) {
                 return customers.get(position++);
             }
             return null;
         }
     }
     ```

4. **Observer Pattern**:
   - **Used In**: Notification System
   - **Description**: Defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically. For example, notifying staff when a room booking is made.
   - **Code Example**:
     ```java
     public interface Observer {
         void update(String message);
     }

     public class Staff implements Observer {
         private String name;

         public Staff(String name) {
             this.name = name;
         }

         @Override
         public void update(String message) {
             System.out.println(name + " received notification: " + message);
         }
     }

     public class BookingNotifier {
         private List<Observer> observers;

         public BookingNotifier() {
             observers = new ArrayList<>();
         }

         public void addObserver(Observer observer) {
             observers.add(observer);
         }

         public void notifyObservers(String message) {
             for (Observer observer : observers) {
                 observer.update(message);
             }
         }
     }
     ```

5. **Strategy Pattern**:
   - **Used In**: Payment Processing
   - **Description**: Defines a family of algorithms, encapsulates each one, and makes them interchangeable. The strategy pattern lets the algorithm vary independently from clients that use it. For example, different payment methods like Credit Card and PayPal.
   - **Code Example**:
     ```java
     public interface PaymentStrategy {
         void pay(double amount);
     }

     public class CreditCardPayment implements PaymentStrategy {
         @Override
         public void pay(double amount) {
             // Process credit card payment
         }
     }

     public class PayPalPayment implements PaymentStrategy {
         @Override
         public void pay(double amount) {
             // Process PayPal payment
         }
     }

     public class PaymentContext {
         private PaymentStrategy strategy;

         public void setPaymentStrategy(PaymentStrategy strategy) {
             this.strategy = strategy;
         }

         public void executePayment(double amount) {
             strategy.pay(amount);
         }
     }
     ```

6. **Chain of Responsibility Pattern**:
   - **Used In**: Request Handling
   - **Description**: Avoids coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. Chains the receiving objects and passes the request along the chain until an object handles it. For example, handling different types of service requests in a hotel.
   - **Code Example**:
     ```java
     public abstract class ServiceHandler {
         protected ServiceHandler nextHandler;

         public void setNextHandler(ServiceHandler nextHandler) {
             this.nextHandler = nextHandler;
         }

         public abstract void handleRequest(String request);
     }

     public class RoomServiceHandler extends ServiceHandler {
         @Override
         public void handleRequest(String request) {
             if (request.equals("RoomService")) {
                 System.out.println("Handling room service request.");
             } else if (nextHandler != null) {
                 nextHandler.handleRequest(request);
             }
         }
     }

     public class LaundryServiceHandler extends ServiceHandler {
         @Override
         public void handleRequest(String request) {
             if (request.equals("LaundryService")) {
                 System.out.println("Handling laundry service request.");
             } else if (nextHandler != null) {
                 nextHandler.handleRequest(request);
             }
         }
     }
     ```

## Project Structure

- `src/`: Contains the source code for the application.
- `bin/`: Contains the compiled Java classes.
- `config/`: Contains configuration files for the application.
- `README.md`: Project documentation.

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests with your changes. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any queries or issues, please contact [praths71018](https://github.com/praths71018).
