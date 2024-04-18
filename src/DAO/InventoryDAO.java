package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.MongoDBConnection;
import org.bson.Document;

public class InventoryDAO {

    private MongoCollection<Document> inventoryCollection;

    public InventoryDAO() {
        MongoDatabase database = MongoDBConnection.getConnection();
        this.inventoryCollection = database.getCollection("inventory");
    }

    public void insertInventoryData() {
        // Data from JSON
        Document[] inventoryData = {
                new Document("itemName", "Towels").append("quantity", 100),
                new Document("itemName", "Toiletries").append("quantity", 200),
                new Document("itemName", "Pillows").append("quantity", 50)
        };

        // Insert data into inventory collection
        for (Document item : inventoryData) {
            inventoryCollection.insertOne(item);
        }
    }
}
