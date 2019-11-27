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
        //List<String> out = searchByChamp("entry", "A", database);
        /*for (String data:
                out) {
            System.out.println(data);
        }*/
        search("A or C or inte", database );
    }

    public static void search(String line, MongoDatabase db){
        String[] token = line.split(" ");

        List<String> dataList = new ArrayList<>();
        List<String> dataListId = new ArrayList<>();
        List<String> dataListName = new ArrayList<>();
        List<String> dataListDesc = new ArrayList<>();

        //ID OR/AND NAME OR/AND DESCRIPTION

        if(token[0]!="."){
            dataListId.addAll(searchByChamp("entry",token[0],db));
        }
        if(token[2]!="."){
            dataListName.addAll(searchByChamp("entryName",token[2],db));
        }
        if(token[4]!="."){
            dataListDesc.addAll(searchByChamp("geneOntologyGO",token[4],db));
        }

        dataList.addAll(dataListId);

        if(token[1].toLowerCase().contains("and")){
            List<String> temp = new ArrayList<>();
            temp.addAll(dataList);
            for (String data:temp) {
                if(!dataListName.contains(data)){
                    dataList.remove(data);
                }
            }
        }
        if(token[3].toLowerCase().contains("and")){
            List<String> temp2 = new ArrayList<>();
            temp2.addAll(dataList);
            for (String data:temp2) {
                if(!dataListDesc.contains(data)){
                    dataList.remove(data);
                }
            }
        }

        if(token[1].toLowerCase().contains("or")){
            for (String data:dataListName) {
                if(!dataList.contains(data)){
                    dataList.add(data);
                }
            }
        }
        if(token[3].toLowerCase().contains("or")){
            for (String data:dataListDesc) {
                if(!dataList.contains(data)){
                    dataList.add(data);
                }
            }
        }
        for(String data: dataList){
            System.out.println(data);
        }


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
