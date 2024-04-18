package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class FoodOrder {
    private String foodName;
    private int quantity;
    private String customerName;
    private FoodItem foodItem; // Reference to the corresponding FoodItem

    public FoodOrder(String foodName, int quantity, String customerName) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.customerName = customerName;
        this.foodItem = retrieveFoodItem(); // Retrieve the associated FoodItem
    }

    // Retrieve the FoodItem corresponding to this order
    private FoodItem retrieveFoodItem() {
        MongoDatabase database = MongoDBConnection.getConnection();
        MongoCollection<Document> collection = database.getCollection("foodItems");

        Document query = new Document("foodName", foodName);
        Document foodItemDoc = collection.find(query).first();

        if (foodItemDoc != null) {
            double price = foodItemDoc.getDouble("price");
            return new FoodItem(foodName, price); // Create FoodItem object
        }

        return null; // FoodItem not found
    }

    public void saveToDatabase() {
        MongoDatabase database = MongoDBConnection.getConnection();
        MongoCollection<Document> collection = database.getCollection("foodOrders");

        Document orderDoc = new Document("foodName", foodName)
                .append("quantity", quantity)
                .append("customerName", customerName);

        collection.insertOne(orderDoc);
    }

    public static List<FoodOrder> getOrdersByCustomer(String customerName) {
        MongoDatabase database = MongoDBConnection.getConnection();
        MongoCollection<Document> collection = database.getCollection("foodOrders");

        List<FoodOrder> orders = new ArrayList<>();
        for (Document doc : collection.find(new Document("customerName", customerName))) {
            String foodName = doc.getString("foodName");
            int quantity = doc.getInteger("quantity");
            orders.add(new FoodOrder(foodName, quantity, customerName));
        }
        return orders;
    }

    public void deleteFromDatabase() {
        MongoDatabase database = MongoDBConnection.getConnection();
        MongoCollection<Document> collection = database.getCollection("foodOrders");

        Document query = new Document("foodName", foodName)
                .append("customerName", customerName);
        collection.deleteOne(query);
    }

    // Calculate the total cost of this order using the associated FoodItem price
    public double getTotalCost() {
        if (foodItem != null) {
            System.out.println("Total cost "+ foodItem.getPrice() * quantity);
            return foodItem.getPrice() * quantity;
        }
        return 0.0; // Handle case where FoodItem is not found
    }

    // Other getters and setters
}
