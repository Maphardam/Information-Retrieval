package com.GooglePP.app.GooglePP;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class WebCrawler {
	
	ArrayList<String> alreadyCrawled;
	String[] blacklist;
	
	public static void main (String[] args){
		WebCrawler wc = new WebCrawler();
		wc.crawl("http://cs.ovgu.de");
	}
	
	public WebCrawler(){
		alreadyCrawled = new ArrayList<String>();
		blacklist = new String[8];
		blacklist[0] = "b_primary_content";
		blacklist[1] = "b_primary_navi_box";
		blacklist[2] = "b_primary_navi_selectbox";
		blacklist[3] = "b_content_servicebox";
		blacklist[4] = "b_headservice";
		blacklist[5] = "b_breadcrumb_navi";
		blacklist[6] = "b_serach";
		blacklist[7] = "b_language";
	}
	
	public void crawl (String url){
		try {
			// get the base URL the URL is redirecting to
			String  baseURL = Jsoup.connect(url).followRedirects(true).execute().url().toExternalForm();
			// get the Document
			// NOTE: if its not a readable file (e.g. a pdf), 
			// it will throw a MalformedURLException, which is catched by the IOException
			Document doc = Jsoup.connect(baseURL).get();
			System.out.println(url);

			// don't visit this URL a second time
			alreadyCrawled.add(url);
			
			// TODO do sth with the document
			
			
			// get all URLs referred to in this document
			Elements links = doc.select("a[href]");
			for(Element link: links){
				String absHref = link.attr("abs:href");
				
				// crawl only sites that are part of cs.uni-ḿagdeburg.de, are not already crawled and are not part of the
				// blacklist
				if(absHref.contains("cs.uni-magdeburg.de") && !alreadyCrawled.contains(absHref) && !containsBlacklist(absHref)){
					crawl(absHref);
				}
			}
		} catch (IOException e) {}

	}
	
	private boolean containsBlacklist(String url){
		for (int i = 0; i < blacklist.length; i++)
			if (url.contains(blacklist[i]))
				return true;		
		return false;
	}
}
