package com.GooglePP.app.GooglePP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class WebCrawler {
	
	static ArrayList<String> alreadyCrawled;
	static String[] blacklist;
	
	public static ArrayList<Doc> crawl (String url, int depth){
		alreadyCrawled = new ArrayList<String>();
		blacklist = new String[9];
		blacklist[0] = "b_primary_content";
		blacklist[1] = "b_primary_navi_box";
		blacklist[2] = "b_primary_navi_selectbox";
		blacklist[3] = "b_content_servicebox";
		blacklist[4] = "b_headservice";
		blacklist[5] = "b_breadcrumb_navi";
		blacklist[6] = "b_serach";
		blacklist[7] = "b_language";
		blacklist[8] = "app.readspeaker";
		
		String baseURL = "";
		try {
			baseURL = Jsoup.connect(url).followRedirects(true).execute().url().toExternalForm();
		} catch (IOException e) {
			System.err.println("An error occured.");
			return new ArrayList<Doc>();
		}
		return _crawl(baseURL, new ArrayList<Doc>(), depth);
	}
	//TODO mache static
	public static ArrayList<Doc> _crawl (String url, ArrayList<Doc> docs, int depth){
		
		try {
			
			// don't visit this URL a second time
			alreadyCrawled.add(url);
			
			// get the Document
			// NOTE: if its not a readable file (e.g. a pdf), 
			// it will throw a MalformedURLException, which is catched by the IOException
			Document doc = Jsoup.connect(url).get();
			
			// read the content
			String plainText = doc.body().toString();
			Pattern p = Pattern.compile(".*<!-- RSPEAK_START -->(.*)<!-- RSPEAK_STOP -->.*", Pattern.DOTALL);
			Matcher m = p.matcher(plainText);
			//System.out.println(plainText);
			m.find();
			String text = Jsoup.parse(m.group(1)).text();
			//System.out.println(text);

			Doc currentSite = new Doc(url, doc.title(), text);
			docs.add(currentSite);
			
			// if the maximum depth is reached, stop here
			if (depth == 0) return docs;
			
			// get all URLs referred to in this document
			Elements links = doc.select("a[href]");
			for(Element link: links){
				String absHref = link.attr("abs:href");
				
				//this is the case, if it's a javascript command and not a real URL
				if (absHref == "") continue;
				
				//normalize links
				String baseAbsHref = Jsoup.connect(absHref).followRedirects(true).execute().url().toExternalForm();	
				
				// TODO if the start page has a #, it is not noticed :(
				if (baseAbsHref.endsWith("#"))
					baseAbsHref = baseAbsHref.substring(0, baseAbsHref.length() - 1);
				
				// crawl only sites that are part of cs.uni-ḿagdeburg.de, are not already crawled and are not part of the
				// blacklist
				if(isValidSubdomain(baseAbsHref))
					_crawl(baseAbsHref, docs, depth - 1);
			}
		} catch (IOException e) {}
		
		return docs;
	}
	
	private static boolean containsBlacklist(String url){
		for (int i = 0; i < blacklist.length; i++)
			if (url.contains(blacklist[i]))
				return true;		
		return false;
	}
	
	private static boolean isValidSubdomain(String url){
		return url.contains("cs.uni-magdeburg.de") && !alreadyCrawled.contains(url) && !containsBlacklist(url);
	}
}
