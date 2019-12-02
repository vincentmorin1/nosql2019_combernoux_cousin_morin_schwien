package relations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReadJson {
	public static void main(String[] args) throws Exception  
    { 
		JSONParser parser = new JSONParser();
		ArrayList<String> protnames = new ArrayList<String>();
		ArrayList<String> protdomains = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("/Users/comberno1u/Documents/nosql/relations/src/unreviewedProtein.json"));
		String line;
		br.readLine();
		while ((line = br.readLine()) != null) {
			JSONObject a = (JSONObject) parser.parse(line);
			String name= (String) a.get("entry");
			protnames.add(name);
			String domain= (String) a.get("crossReferenceInterPro");
			protdomains.add(domain);
		}
		br.close();
     
		//calcul des poids
		
		int i=0;
		int j=0;
		FileWriter fileWriter = new FileWriter("MonFichier.csv");
		for(String prot1 : protdomains) {
			for(String prot2:protdomains) {
				if (i!=j && j>i) {
					int poidsh=0;
					int poidsb=0;

					String[] parts1 = protdomains.get(i).split(";");
					String[] parts2 = protdomains.get(j).split(";");
					ArrayList<String> bas= new ArrayList<String>();
					for (String p : parts1){
						for (String p2 : parts2){
					
							if(p.equals(p2)) {
								poidsh+=1;
							}
							int b1=0;
							int b2=0;
							for (String s: bas) {
								if(s.equals(p)) {
									b1=1;
									
								}
								if(s.equals(p2)) {
									b2=1;
									
								}
							}
							
							if (b1==0) {
								bas.add(p);
								
							}
							if (b2==0) {
								bas.add(p2);
						
							}
							
						}
					}
					poidsb=bas.size();
					fileWriter.write("\""+protnames.get(i)+"\",\""+protnames.get(j)+"\",\""+poidsh+"/"+poidsb+"\""+"\n");
					
				}
				j=j+1;
				
			}
			i=i+1;
			j=0;
		}
		fileWriter.close();
		
            } 
       
    } 