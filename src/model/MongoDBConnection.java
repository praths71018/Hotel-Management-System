package model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static final String DATABASE_NAME = "HotelDB";
    private static final String CONNECTION_STRING = "mongodb://localhost:27017/" + DATABASE_NAME;

    public static MongoDatabase getConnection() {
        MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);
        return mongoClient.getDatabase(DATABASE_NAME);
    }
}
