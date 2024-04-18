package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

public class Bill {
    private String customerName;
    private double totalAmount;
    private double roomAmount; // Represents the amount for the room

    private static final String COLLECTION_NAME = "bills";

    public Bill(String customerName, double roomAmount) {
        this.customerName = customerName;
        this.roomAmount = roomAmount;
        this.totalAmount = calculateTotalAmountFromOrders(); // Initialize total amount including room amount
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private double calculateTotalAmountFromOrders() {
        // Retrieve orders for this customer and calculate total cost
        List<FoodOrder> orders = FoodOrder.getOrdersByCustomer(this.customerName);
        double totalAmountFromOrders = orders.stream()
                .mapToDouble(FoodOrder::getTotalCost)
                .sum();

        // Add room amount to the total amount from orders
        return totalAmountFromOrders + this.roomAmount;
    }

    public void saveToMongoDB() {
        try {
            MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            Document query = new Document("customerName", this.customerName);
            Document updateDoc = new Document("$set", new Document("totalAmount", this.totalAmount));
            collection.updateOne(query, updateDoc);
        } catch (Exception e) {
            System.err.println("Error saving bill to MongoDB: " + e.getMessage());
        }
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
