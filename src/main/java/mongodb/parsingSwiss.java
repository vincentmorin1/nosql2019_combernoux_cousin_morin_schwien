package mongodb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class parsingSwiss {

    public static void main (String[] args) {
        try (Scanner scanner = new Scanner(new File("./VersionSWISS.tab"));) {
            int nword = 0;
            while (scanner.hasNextLine() && nword<2) {
                String sent = scanner.nextLine();
                nword++;
                System.out.printf("%3d) %s%n", nword, sent);
                String data[] = sent.split("\t");
                for( int i=0;i<data.length; i++ ){
                    System.out.println(data[i]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

