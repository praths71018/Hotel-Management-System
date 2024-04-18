package controller;
import model.*;
import java.util.List;

public class HotelController {
    public boolean checkIn(String name, String id, String phoneNumber, String address, int roomNumber) {
        Room room = Room.getRoomByNumber(roomNumber);
        if (room != null && !room.isBooked()) {
            room.setBookedStatus(true);
            room.updateRoomStatusInDatabase();

            Customer customer = new Customer(name, id, phoneNumber, address, roomNumber);
            customer.saveToDatabase();
            return true;
        }
        return false;
    }



    public void checkOut(int roomNumber) {
        Room room = Room.getRoomByNumber(roomNumber);
        if (room != null && room.isBooked()) {
            // Delete customer and associated bill
            Customer customer = Customer.getCustomerByRoomNumber(roomNumber);
            assert customer != null;
            String cust=customer.getName();
            Customer.deleteCustomerByRoomNumber(roomNumber);

            // Delete food orders associated with the customer
            List<FoodOrder> orders = FoodOrder.getOrdersByCustomer(cust);
            for (FoodOrder order : orders) {
                order.deleteFromDatabase();
            }

            // Unbook the room
            room.setBookedStatus(false);
            room.updateRoomStatusInDatabase();
        }
    }

    public void orderFood(String foodName, int quantity, String customerName) {
        FoodOrder order = new FoodOrder(foodName, quantity, customerName);
        order.saveToDatabase();
    }

    public Bill getBill(int roomNumber) {
        Customer customer = Customer.getCustomerByRoomNumber(roomNumber);
        if (customer != null) {
            Room room = Room.getRoomByNumber(roomNumber);
            if (room != null && room.isBooked()) {
                double roomPrice = room.getPrice(); // Retrieve the room price
                Bill bill = new Bill(customer.getName(), roomPrice); // Create bill for the customer including room price
                bill.saveToMongoDB(); // Save the bill to MongoDB
                return bill;
            }
        }
        return null; // Handle case where customer or room is not found or room is not booked
    }


    public boolean addInventoryItem(String itemName, int quantity, double price) {
        return Inventory.addItem(itemName, quantity, price);
    }

    public void deleteInventoryItem(String itemName) {
        Inventory.deleteItem(itemName);
    }

    public void addFoodItem(String foodName, double price) {
        FoodItem foodItem = new FoodItem(foodName, price);
        foodItem.saveToDatabase();
    }

    public void deleteFoodItem(String foodName) {
        FoodItem.deleteItem(foodName);
    }

    public boolean checkRoomAvailability(int roomNumber) {
        Room room = Room.getRoomByNumber(roomNumber);
        return room != null && !room.isBooked();
    }

    public List<Room> getAllAvailableRooms() {
        return Room.getAllAvailableRooms();
    }

    public String getAllInventoryItems() {
        return Inventory.getAllItems();
    }

    public String getAllFoodItems() {
        return FoodItem.getAllItems();
    }
}
