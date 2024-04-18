package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.MongoDBConnection;
import org.bson.Document;

public class RoomDAO {

    private MongoCollection<Document> roomCollection;

    public RoomDAO() {
        MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
        this.roomCollection = database.getCollection("rooms");
    }

    public void insertRoomData() {
        // Data from JSON
        Document[] roomData = {
                new Document("roomNumber", 101).append("roomType", "Standard").append("isBooked", false),
                new Document("roomNumber", 102).append("roomType", "Deluxe").append("isBooked", false),
                new Document("roomNumber", 103).append("roomType", "Suite").append("isBooked", true)
        };

        // Insert data into rooms collection
        for (Document room : roomData) {
            roomCollection.insertOne(room);
        }
    }
}

