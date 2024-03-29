package mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class mongodbConnection {

    public static void main (String[] args) {
        mongodbSetup();
    }

    public static void mongodbSetup(){

        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("mydb");
            MongoCollection<Document> collectionSwissProtein = database.getCollection("swissProtein");
            MongoCollection<Document> collectionUnreviewedProtein = database.getCollection("unreviewedProtein");

            database.drop();

            Populate("./VersionSWISS.tab", collectionSwissProtein);
            Populate("./VersionUNREVIEWED.tab", collectionUnreviewedProtein);
        } catch (MongoException mongoExObj){
            mongoExObj.printStackTrace();
        }
    }

    private static void Populate(String path, MongoCollection<Document> collection){
        try (Scanner scanner = new Scanner(new File(path));) {
            int i = 0;
            while (scanner.hasNextLine() && i<15) {
                String sent = scanner.nextLine();
                i++;
                String data[] = sent.split("\t",12);
                Document doc = new Document("entry", data[0])
                        .append("entryName", data[1])
                        .append("status", data[2])
                        .append("proteinNames", data[3])
                        .append("geneNames", data[4])
                        .append("organism", data[5])
                        .append("length", data[6])
                        .append("crossReferenceInterPro", data[7])
                        .append("sequence", data[8])
                        .append("geneOntologyGO", data[9])
                        .append("functionCC", data[10])
                        .append("eCNumber", data[11]);
                collection.insertOne(doc);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}