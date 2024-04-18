package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Receptionist {
    private MongoCollection<Document> receptionistCollection;

    public Receptionist() {
        MongoDatabase database = MongoDBConnection.getConnection();
        this.receptionistCollection = database.getCollection("receptionists");
    }

    public boolean checkRoomAvailability(int roomNumber) {
        // Method to check room availability
        return true; // Placeholder implementation
    }

    public void recordComplaint(String managerName, String complaint) {
        Document complaintDoc = new Document("managerName", managerName)
                .append("complaint", complaint);
        receptionistCollection.insertOne(complaintDoc);
    }

    public List<Document> viewComplaints() {
        List<Document> complaints = new ArrayList<>();

        for (Document doc : receptionistCollection.find()) {
            complaints.add(doc);
        }

        return complaints;
    }
}
