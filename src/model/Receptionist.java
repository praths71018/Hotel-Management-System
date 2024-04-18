package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Receptionist {
    private MongoCollection<Document> receptionistCollection;

    public Receptionist() {
        try {
            MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
            this.receptionistCollection = database.getCollection("receptionists");
        } catch (Exception e) {
            System.err.println("Error initializing Receptionist collection: " + e.getMessage());
        }
    }

    public boolean checkRoomAvailability(int roomNumber) {
        try {
            // Placeholder implementation to check room availability
            return true;
        } catch (Exception e) {
            System.err.println("Error checking room availability: " + e.getMessage());
            return false;
        }
    }

    public void recordComplaint(String managerName, String complaint) {
        try {
            Document complaintDoc = new Document("managerName", managerName)
                    .append("complaint", complaint);
            receptionistCollection.insertOne(complaintDoc);
        } catch (Exception e) {
            System.err.println("Error recording complaint: " + e.getMessage());
        }
    }

    public List<Document> viewComplaints() {
        List<Document> complaints = new ArrayList<>();

        try {
            for (Document doc : receptionistCollection.find()) {
                complaints.add(doc);
            }
        } catch (Exception e) {
            System.err.println("Error viewing complaints: " + e.getMessage());
        }

        return complaints;
    }
}
