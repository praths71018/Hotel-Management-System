package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class FoodItem {
    private String foodName;
    private double price;

    public FoodItem(String foodName, double price) {
        this.foodName = foodName;
        this.price = price;
    }

    public void saveToDatabase() {
        MongoDatabase database = MongoDBConnection.getConnection();
        MongoCollection<Document> collection = database.getCollection("foodItems");

        Document foodItemDoc = new Document("foodName", foodName)
                .append("price", price);

        collection.insertOne(foodItemDoc);
    }

    public static void deleteItem(String foodName) {
        MongoDatabase database = MongoDBConnection.getConnection();
        MongoCollection<Document> collection = database.getCollection("foodItems");

        Document query = new Document("foodName", foodName);
        collection.deleteOne(query);
    }

    public static String getAllItems() {
        MongoDatabase database = MongoDBConnection.getConnection();
        MongoCollection<Document> collection = database.getCollection("foodItems");

        StringBuilder items = new StringBuilder();
        for (Document doc : collection.find()) {
            String foodName = doc.getString("foodName");
            double price = doc.getDouble("price");
            items.append(foodName).append(" - Price: $").append(price).append("\n");
        }
        return items.toString();
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
