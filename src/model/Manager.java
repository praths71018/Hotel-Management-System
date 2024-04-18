package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private MongoCollection<Document> managerCollection;

    public Manager() {
        MongoDatabase database = MongoDBConnection.getConnection();
        this.managerCollection = database.getCollection("managers");
    }

    public void updateInventoryItem(String itemName, int newQuantity) {
        // Method to update inventory item quantity
    }

    public void recordComplaint(String receptionistName, String complaint) {
        Document complaintDoc = new Document("receptionistName", receptionistName)
                .append("complaint", complaint);
        managerCollection.insertOne(complaintDoc);
    }

    public List<Document> viewComplaints() {
        List<Document> complaints = new ArrayList<>();

        for (Document doc : managerCollection.find()) {
            complaints.add(doc);
        }

        return complaints;
    }
}
