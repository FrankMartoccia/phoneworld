package it.unipi.dii.lsmsdb.phoneworld.repository;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.model.User;

import org.bson.Document;

public class MockPhoneWorld {

    public MockPhoneWorld() {
    }

    public void initMock() {
        ConnectionString uri = new ConnectionString("mongodb://localhost:27017/testPhoneWorld");
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase db = mongoClient.getDatabase("testPhoneWorld");
        MongoCollection<Document> phones = db.getCollection("Phones");
        MongoCollection<Document> reviews = db.getCollection("Reviews");
        MongoCollection<Document> users = db.getCollection("Users");
        //db.getCollection("Phones").drop(); TODO in test
        //db.getCollection("Reviews").drop(); TODO in test
        //db.getCollection("Users").drop(); TODO in test
    }

    public void initUsers() {
        //Document user = new Document("")
    }

}
