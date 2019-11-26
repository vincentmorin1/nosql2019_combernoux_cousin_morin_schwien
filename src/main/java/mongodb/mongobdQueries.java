package mongodb;

import com.mongodb.client.*;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class mongobdQueries {

    public static void main (String[] args) {

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("mydb");
        searchByID("A0A1B0GW15", database);
        searchByName("A0A1B0GW15_HUMAN", database);
    }

    public static void searchByID(String id, MongoDatabase database){
        MongoCollection<Document> collectionSwissProtein = database.getCollection("swissProtein");
        collectionSwissProtein.createIndex(new Document("entry", 1));
        MongoCollection<Document> collectionUnreviewedProtein = database.getCollection("unreviewedProtein");
        collectionUnreviewedProtein.createIndex(new Document("entry", 1));
        Document myDoc;
        MongoCursor<Document> cursor = collectionSwissProtein.find(eq("entry",id)).iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
        MongoCursor<Document> cursor2 = collectionUnreviewedProtein.find(eq("entry",id)).iterator();
        try {
            while (cursor2.hasNext()) {
                System.out.println(cursor2.next().toJson());
            }
        } finally {
            cursor2.close();
        }
    }

    public static void searchByName(String name, MongoDatabase database){
        MongoCollection<Document> collectionSwissProtein = database.getCollection("swissProtein");
        collectionSwissProtein.createIndex(new Document("entryName", 1));
        MongoCollection<Document> collectionUnreviewedProtein = database.getCollection("unreviewedProtein");
        collectionUnreviewedProtein.createIndex(new Document("entryName", 1));
        Document myDoc;
        MongoCursor<Document> cursor = collectionSwissProtein.find(eq("entryName",name)).iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
        MongoCursor<Document> cursor2 = collectionUnreviewedProtein.find(eq("entryName",name)).iterator();
        try {
            while (cursor2.hasNext()) {
                System.out.println(cursor2.next().toJson());
            }
        } finally {
            cursor2.close();
        }
    }
}
