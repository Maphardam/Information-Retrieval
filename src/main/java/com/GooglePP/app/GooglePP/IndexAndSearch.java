package com.GooglePP.app.GooglePP;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexAndSearch {
	
	//name of the target directory
	static String indexPath = "index";
	
	/**
	 * indexes a list of Docs to a directory
	 * @param docs a list of com.GooglePP.app.GooglePP.Doc
	 */
	public static void indexDocs(List<Doc> docs){
		try {
		
		//create the target dir
		Directory dir = null;
		dir = FSDirectory.open(new File(indexPath));

		//load the documents
		List<Document> documents = new ArrayList<Document>();
		for (Doc d: docs) documents.add(d.toDocument());
		
		//define an analyzer, which preprocesses the text
		Analyzer analyzer = new StandardAnalyzer();
		
		//hmm yeah, i'm sure this is important as well...
		//jk, this is the configuration of the index writer -> we will always create a new index
	    IndexWriterConfig iwc = new IndexWriterConfig(Version.LATEST, analyzer);
	    iwc.setOpenMode(OpenMode.CREATE);
	    
	    //set our index writer
	    IndexWriter writer = new IndexWriter(dir, iwc);

	    //add all docs to the index
	    for (Document d: documents)
			writer.addDocument(d);
	    
	    writer.close();
	    
		} catch (IOException e){System.out.println("Error");};
	    
	}
	/**
	 * searches the index for a query text
	 * @param queryText a string containing the query text
	 */
	public static void searchDocs(String queryText){
		try {
			
		//define the index searcher and the dir of our index
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
	    IndexSearcher searcher = new IndexSearcher(reader);
	    
	    //IMPORTANT: use the same analyzer for querying as used for indexing
	    Analyzer analyzer = new StandardAnalyzer();
	    //query the queryText to a correct query
	    //so far, we only consider terms in the 'text' field
	    QueryParser parser = new QueryParser("text", analyzer);
	    Query q = parser.parse(queryText);    
	    System.out.println(q);
	    
	    //search the docs containing the query
	    ScoreDoc[] sd = searcher.search(q, 10).scoreDocs;
	    System.out.println(Arrays.toString(sd));
	    
		} catch (IOException e){System.out.println("Error");} catch (ParseException e) {System.out.println("Another Error");}
	}
	
}
