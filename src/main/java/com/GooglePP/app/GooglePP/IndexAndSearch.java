package com.GooglePP.app.GooglePP;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexAndSearch {

	/**
	 * indexes a list of Docs to a directory
	 * 
	 * @param indexPath
	 *            the path to the index
	 * @param docs
	 *            a list of com.GooglePP.app.GooglePP.Doc
	 * @return the index searcher to the newly created index, which is used for
	 *         searching querys
	 */
	public static IndexSearcher indexDocs(String indexPath, List<Doc> docs) {
		IndexSearcher searcher = null;

		try {
			// create the target dir
			Directory dir = null;
			dir = FSDirectory.open(new File(indexPath));

			// load the documents
			List<Document> documents = new ArrayList<Document>();
			for (Doc d : docs)
				documents.add(d.toDocument());

			// define an analyzer, which preprocesses the text
			Analyzer analyzer = new StandardAnalyzer();

			// hmm yeah, i'm sure this is important as well...
			// jk, this is the configuration of the index writer -> we will
			// always create a new index
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LATEST, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);

			// set our index writer
			IndexWriter writer = new IndexWriter(dir, iwc);

			// add all docs to the index
			for (Document d : documents)
				writer.addDocument(d);

			writer.close();

			searcher = loadIndex(indexPath);
		} catch (IOException e) {
			System.err.println("couldn't create index at " + indexPath);
			e.printStackTrace();
		}

		return searcher;
	}

	/**
	 * load index
	 * 
	 * @param indexPath
	 *            the path to the location of the index
	 * @return
	 * @return the index searcher which is used for searching querys (null if
	 *         the index was not found)
	 */
	public static IndexSearcher loadIndex(String indexPath) {
		IndexSearcher searcher = null;
		try {
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
			searcher = new IndexSearcher(reader);
		} catch (IOException e) {
			System.err.println("couldn't load index at :" + indexPath);
			e.printStackTrace();
		}
		return searcher;
	}

	/**
	 * searches the index for a query text
	 * 
	 * @param indexPath
	 *            the path to the index
	 * @param queryText
	 *            a string containing the query text
	 * @return the 10 best ranked documents, matching the query
	 */
	public static TopDocs searchDocs(IndexSearcher searcher, String queryText) {
		TopDocs td = null;
		try {

			// IMPORTANT: use the same analyzer for querying as used for
			// indexing
			Analyzer analyzer = new StandardAnalyzer();

			// query the queryText to a correct query
			String[] fields = { "text", "title"};
			MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
			Query q = parser.parse(queryText);
			td = searcher.search(q, 10);

		} catch (IOException e) {
			System.err.println("Error");
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("Couldn't parse the query correctly!");
			e.printStackTrace();
		}
		return td;
	}

}
