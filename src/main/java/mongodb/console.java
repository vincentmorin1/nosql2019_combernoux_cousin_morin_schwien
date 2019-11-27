package mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.Scanner;

public class console {

    public static void main(String[] args){
        //mongodbConnection.mongodbSetup();
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = mongoClient.getDatabase("mydb");
        System.out.println("Bienvenue dans la console de requête !");
        Scanner input = new Scanner(System.in);
        String s = "Saucisse";
        while (s.toLowerCase()!= "exit"){
            System.out.println("Entrer une requête : (taper 'exit' pour quitter)");
            System.out.println("Format de données : 'ID' AND/OR 'Name' AND/OR 'Description'");
            System.out.println("Taper '.' pour laisser un champ vide");
            System.out.println();
            s = input.nextLine();
            if (s.toLowerCase()!="exit"){
                mongobdQueries.search(s, db);
            }
        }

    }
}
