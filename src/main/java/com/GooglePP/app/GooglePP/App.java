package com.GooglePP.app.GooglePP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
	
		
			String filePath = "reut2-000.xml";
			List<Doc> docs = XmlParser.getDocs(filePath);
			
		try {	
			InputStreamReader isr = new InputStreamReader(System.in);
		    BufferedReader br = new BufferedReader(isr);
		    System.out.println("Bitte geben Sie ihre Query an.");
		    String query = br.readLine();
			System.out.println("Für ihre Eingabe "+ query + " wurden diese Artikel gefunden:");
			IndexAndSearch.searchDocs(query);
			//use this command for creating an index
			//IndexAndSearch.indexDocs(docs);
			//use this command for querying
		    

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	
    } 
    
}
