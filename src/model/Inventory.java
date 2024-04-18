package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Inventory {
    public static boolean addItem(String itemName, int quantity, double price) {
        MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
        MongoCollection<Document> collection = database.getCollection("inventory");

        Document itemDoc = new Document("itemName", itemName)
                .append("quantity", quantity);

        collection.insertOne(itemDoc);
        return true;
    }

    public static void deleteItem(String itemName) {
        MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
        MongoCollection<Document> collection = database.getCollection("inventory");

        Document query = new Document("itemName", itemName);
        collection.deleteOne(query);
    }

    public static String getAllItems() {
        MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
        MongoCollection<Document> collection = database.getCollection("inventory");

        StringBuilder items = new StringBuilder();
        for (Document doc : collection.find()) {
            String itemName = doc.getString("itemName");
            int quantity = doc.getInteger("quantity");
            items.append(itemName).append(" - Quantity: ").append(quantity).append(", Price: $").append("\n");
        }
        return items.toString();
    }

    // Other getters and setters
}
