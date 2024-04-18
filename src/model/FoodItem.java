package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class FoodItem {
    private String foodName;
    private double price;

    private static final String COLLECTION_NAME = "foodItems";

    public FoodItem(String foodName, double price) {
        this.foodName = foodName;
        this.price = price;
    }

    public void saveToDatabase() {
        try {
            MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Document foodItemDoc = new Document("foodName", foodName)
                    .append("price", price);

            collection.insertOne(foodItemDoc);
        } catch (Exception e) {
            System.err.println("Error saving food item to MongoDB: " + e.getMessage());
        }
    }

    public static void deleteItem(String foodName) {
        try {
            MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Document query = new Document("foodName", foodName);
            collection.deleteOne(query);
        } catch (Exception e) {
            System.err.println("Error deleting food item from MongoDB: " + e.getMessage());
        }
    }

    public static String getAllItems() {
        try {
            MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            StringBuilder items = new StringBuilder();
            for (Document doc : collection.find()) {
                String foodName = doc.getString("foodName");
                double price = doc.getDouble("price");
                items.append(foodName).append(" - Price: $").append(price).append("\n");
            }
            return items.toString();
        } catch (Exception e) {
            System.err.println("Error retrieving food items from MongoDB: " + e.getMessage());
            return "";
        }
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    // Other getters and setters
}
