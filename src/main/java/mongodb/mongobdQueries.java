package mongodb;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.*;

import static com.mongodb.client.model.Filters.regex;

public class mongobdQueries {

    public static void main (String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("mydb");
        //entry
        //entryName
        //geneOntologyGO
        List<String> out = searchByChamp("entry", "A", database);
        for (String data:
                out) {
            System.out.println(data);
        }
    }

    public static void search(){

    }

    public static List<String> searchByChamp(String champ, String chain, MongoDatabase database){
        List<String> dataList = new ArrayList<>();
        dataList.addAll(searchCollection(champ, chain, database, "swissProtein"));
        dataList.addAll(searchCollection(champ, chain, database, "unreviewedProtein"));
        return dataList;
    }

    public static List<String> searchCollection(String champ, String chain, MongoDatabase database, String collectionName){
        List<String> data = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.createIndex(new Document(champ, 1));
        MongoCursor<Document> cursor = collection.find(regex(champ, "("+chain+")+", "i")).iterator();
        try {
            while (cursor.hasNext()) {
                data.add(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
        return data;
    }
}
