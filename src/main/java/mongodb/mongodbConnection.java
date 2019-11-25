package mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class mongodbConnection {

    public static void main (String[] args) {
        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("mydb");
            MongoCollection<Document> collectionSwissProtein = database.getCollection("swissProtein");
            MongoCollection<Document> collectionUnreviewedProtein = database.getCollection("unreviewedProtein");

            try (Scanner scanner = new Scanner(new File("./VersionSWISS.tab"));) {
                int nword = 0;
                while (scanner.hasNextLine() && nword<2) {
                    String sent = scanner.nextLine();
                    nword++;
                    System.out.printf("%3d) %s%n", nword, sent);
                    String data[] = sent.split("[\t]|[{]");
                    Document doc = new Document("entry", data[0])
                            .append("entryName", data[1])
                            .append("status", data[2])
                            .append("proteinNames", data[3])
                            .append("geneNames", data[4])
                            .append("organism", data[5])
                            .append("length", data[6])
                            .append("crossReferenceInterPro", data[7])
                            .append("sequence", data[8])
                            .append("geneOntologyGOs", data[9])
                            .append("functionCC", data[10])
                            .append("eCNumber", data[11]);


                    collectionSwissProtein.insertOne(doc);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            Document myDoc = collectionSwissProtein.find().first();
            System.out.println(myDoc.toJson());

        } catch (MongoException mongoExObj){
            mongoExObj.printStackTrace();
        }
    }
}