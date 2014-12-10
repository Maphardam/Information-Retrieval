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
		String indexPath = "index";
		List<Doc> docs = XmlParser.getDocs(filePath);
		boolean exit = false;
		while(!exit)
		{
			try {	
				InputStreamReader isr = new InputStreamReader(System.in);
			    BufferedReader br = new BufferedReader(isr);
			    System.out.println("Bitte geben Sie ihre Query an.");
			    String query = br.readLine();
			    String[] params = query.split(" ");
			    
			    if(params.length > 0)
			    {
			    	if(params[0].equals("exit"))
			    	{
			    		if(params.length == 1)
			    		{
			    			exit = true;
			    		}
			    	}
			    	else if(params[0].equals("index"))
			    	{
			    		if(params.length == 3)
			    		{
			    			docs = XmlParser.getDocs(params[2]);
							IndexAndSearch.indexDocs(params[1] , docs);
			    			indexPath = params[1];
			    		}
			    	}
			    	else if(params[0].equals("load"))
			    	{
			    		if(params.length == 2)
			    		{
			    			indexPath = params[1];
			    		}
			    	}
			    	else
			    	{
						System.out.println("Für ihre Eingabe "+ query + " wurden diese Artikel gefunden:");
						IndexAndSearch.searchDocs(indexPath, query);
			    	}

					//use this command for creating an index
					//IndexAndSearch.indexDocs(docs);
					//use this command for querying
			    }
			    
			    
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    } 
    
}
