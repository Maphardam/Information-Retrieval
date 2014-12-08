package com.GooglePP.app.GooglePP;

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
		
		//use this command for creating an index
		//IndexAndSearch.indexDocs(docs);
		
		//use this command for querying
		IndexAndSearch.searchDocs("Japanese");
    }
}
