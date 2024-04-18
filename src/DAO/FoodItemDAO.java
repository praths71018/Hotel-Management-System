package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.MongoDBConnection;
import org.bson.Document;

public class FoodItemDAO {

    private MongoCollection<Document> foodItemCollection;

    public FoodItemDAO() {
        MongoDatabase database = MongoDBConnection.getConnection();
        this.foodItemCollection = database.getCollection("foodItems");
    }

    public void insertFoodItemData() {
        // Data from JSON
        Document[] foodItemData = {
                new Document("itemName", "Burger").append("price", 10.0),
                new Document("itemName", "Pizza").append("price", 15.0),
                new Document("itemName", "Salad").append("price", 8.0)
        };

        // Insert data into foodItems collection
        for (Document foodItem : foodItemData) {
            foodItemCollection.insertOne(foodItem);
        }
    }
}

