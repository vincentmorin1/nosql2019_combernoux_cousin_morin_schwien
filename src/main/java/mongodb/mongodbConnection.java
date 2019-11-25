package mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import java.util.Arrays;

public class mongodbConnection {

    public static void main (String[] args) {
        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("mydb");
            MongoCollection<Document> collectionReviewedProtein = database.getCollection("reviewedProtein");
            MongoCollection<Document> collectionUnreviewedProtein = database.getCollection("unreviewedProtein");

            Document doc = new Document("name", "MongoDB")
                    .append("type", "database")
                    .append("count", 1)
                    .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                    .append("info", new Document("x", 203).append("y", 102));

            collectionReviewedProtein.insertOne(doc);

            Document myDoc = collectionReviewedProtein.find().first();
            System.out.println(myDoc.toJson());

            collectionReviewedProtein.createIndex(Indexes.text("description"));
            collectionReviewedProtein.createIndex(Indexes.text("name"));
            collectionReviewedProtein.createIndex(Indexes.text("id"));

        } catch (MongoException mongoExObj){
            mongoExObj.printStackTrace();
        }
    }

}
