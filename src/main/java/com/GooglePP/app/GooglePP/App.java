//package com.GooglePP.app.GooglePP;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.List;
//
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TopDocs;
//
///**
// * Hello world!
// *
// */
//public class App {
//	public static void main(String[] args) {
//		//String filePath = "reut2-000.xml";
//		String indexPath = "";
//		IndexSearcher searcher = null;
//		List<Doc> docs = null;
//		boolean exit = false;
//		System.out.println("Please load an existing index, or create a new index first!");
//		System.out.println("Type \"help load\" or \"help create\" for more information.");
//		while (!exit) {
//			try {
//				InputStreamReader isr = new InputStreamReader(System.in);
//				BufferedReader br = new BufferedReader(isr);
//				System.out.print("> ");
//				String query = br.readLine();
//				String[] params = query.split(" ");
//
//				if (params.length > 0) {
//					if (params[0].equals("create")) {
//						if (params.length == 3) {
//							System.out.println("Getting documents from " + params[2] + " .");
//							docs = XmlParser.getDocs(params[2]);
//							System.out.println("Creating index at " + params[1] + ".");
//							IndexAndSearch.indexDocs(params[1], docs);
//							indexPath = params[1];
//							System.out.println("Indexing of " + params[2] + " finished.");
//						}
//						else
//						{
//							System.out.println("Wrong usage of the create command.");
//							System.out.println("see \"help create\" for further information");
//						}
//					} else if (params[0].equals("load")) {
//						if (params.length == 2) {
//							indexPath = params[1];
//							IndexSearcher tmpSearcher = IndexAndSearch.loadIndex(indexPath);
//							if (tmpSearcher != null) {
//								searcher = tmpSearcher;
//								System.out.println("Using the index at " + params[1] + " from now on.");
//							} else {
//								System.err.println("Please enter a valid path to the index.");
//							}
//						}
//						else
//						{
//							System.out.println("Wrong usage of the load command.");
//							System.out.println("see \"help load\" for further information");
//						}
//					} else if (params[0].equals("help")) {
//						if (params.length == 1) {
//							System.out.println("Available Commands:");
//							System.out.println("    create");
//							System.out.println("    load");
//							System.out.println("    search");
//							System.out.println("    exit");
//							System.out.println("If the input does not math with the given commands it will be seen as search query.");
//							System.out.println("type \"help COMMAND\" for more information.");
//						} else {
//							if (params[1].equals("create")) {
//								System.out.println("Creating an index at a given path and index the documents stored in a xml file.");
//								System.out.println("If there already exists an index at INDEXPATH, it will be overwritten.");
//								System.out.println("create INDEXPATH XMLPATH");
//								System.out.println("       INDEXPATH  The path where the index should be saved");
//								System.out.println("       XMLPATH    The path to the xml file, where the documents are stored");
//							} else if (params[1].equals("load")) {
//								System.out.println("Loading an existing index from a specified path.");
//								System.out.println("load PATH");
//								System.out.println("     PATH  The path to the existing index");
//							} else if (params[1].equals("search")) {
//								System.out.println("Searching a loaded index for the 10 best results with a given query.");
//								System.out.println("search QUERY");
//								System.out.println("       QUERY  A query which can be used to search documents in a lucene index.");
//							} else if (params[1].equals("exit")) {
//								System.out.println("Close this application.");
//							} else {
//								System.out.println("Command not found!");
//							}
//						}
//					} else if (params[0].equals("exit")) {
//						if (params.length == 1) {
//							exit = true;
//						}
//						else
//						{
//							System.out.println("Wrong usage of the exit command.");
//							System.out.println("If you want to search for exit use \"search exit\".");
//							System.out.println("Otherwise type \"exit\" to close the application.");
//						}
//					} else {
//						if (params[0].equals("search")) {
//							query = query.substring(7, query.length());
//						}
//						if (searcher != null) {
//							TopDocs td = IndexAndSearch.searchDocs(searcher, query);
//							ScoreDoc[] sd = td.scoreDocs;
//							if (td != null) {
//								if (sd.length >= 10) {
//									System.out.println("The " + sd.length + " best ranked documents out of " + td.totalHits + " matching documents:");
//								} else if (sd.length > 0) {
//									System.out.println(sd.length + " matching documents found:");
//								} else {
//									System.out.println("No documents match with the given Query:");
//								}
//
//								for (int i = 0; i < Math.min(10, sd.length); i++) {
//									ScoreDoc currentdoc = sd[i];
//									System.out.println(i + 1 + ". " + searcher.doc(sd[i].doc).get("title") + "( ID: " + currentdoc.doc + " relevance score: " + currentdoc.score + ")");
//								}
//							}
//						} else {
//							System.out.println("Can't search, if no index is loaded, please use either create or load.");
//							System.out.println("Type help for more information.");
//						}
//					}
//				}
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}
