package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Customer {
    private String name;
    private String id;
    private String phoneNumber;
    private String address;
    private int roomNumber;

    private static final String COLLECTION_NAME = "customers";

    public Customer(String name, String id, String phoneNumber, String address, int roomNumber) {
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roomNumber = roomNumber;
    }

    public void saveToDatabase() {
        MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        Document customerDoc = new Document("name", name)
                .append("id", id)
                .append("phoneNumber", phoneNumber)
                .append("address", address)
                .append("roomNumber", roomNumber);

        collection.insertOne(customerDoc);
    }

    public static Customer getCustomerByRoomNumber(int roomNumber) {
        MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        Document query = new Document("roomNumber", roomNumber);
        Document customerDoc = collection.find(query).first();

        if (customerDoc != null) {
            String name = customerDoc.getString("name");
            String id = customerDoc.getString("id");
            String phoneNumber = customerDoc.getString("phoneNumber");
            String address = customerDoc.getString("address");
            return new Customer(name, id, phoneNumber, address, roomNumber);
        } else {
            return null; // Customer not found
        }
    }

    public static void deleteCustomerByRoomNumber(int roomNumber) {
        MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        Document query = new Document("roomNumber", roomNumber);
        collection.deleteOne(query);
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}

