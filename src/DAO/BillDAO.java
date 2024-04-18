package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.MongoDBConnection;
import org.bson.Document;

public class BillDAO {

    private MongoCollection<Document> billCollection;

    public BillDAO() {
        MongoDatabase database = MongoDBConnection.getConnection();
        this.billCollection = database.getCollection("bills");
    }

    public void insertBillData() {
        // Data from JSON
        Document[] billData = {
                new Document("customerName", "John Doe").append("amount", 150.0),
                new Document("customerName", "Jane Smith").append("amount", 200.0)
        };

        // Insert data into bills collection
        for (Document bill : billData) {
            billCollection.insertOne(bill);
        }
    }
}

