package com.GooglePP.app.GooglePP;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexDocs {

	public static void indexDocs(List<Doc> docs){
		try {
		
		String indexPath = "index";
		Directory dir = null;

		dir = FSDirectory.open(new File(indexPath));

		
		List<Document> documents = new ArrayList<Document>();
		for (Doc d: docs) documents.add(d.toDocument());
		
		Analyzer analyzer = new StandardAnalyzer();
	    IndexWriterConfig iwc = new IndexWriterConfig(Version.LATEST, analyzer);
	    iwc.setOpenMode(OpenMode.CREATE);
	    
	    IndexWriter writer = new IndexWriter(dir, iwc);

	    for (Document d: documents)
			writer.addDocument(d);
	    
	    writer.close();
	    
		} catch (IOException e){System.out.println("Error");};
	    
	}
	
}
