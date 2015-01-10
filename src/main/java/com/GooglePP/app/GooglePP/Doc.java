package com.GooglePP.app.GooglePP;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

/**
 * a class to store information about a document
 */
public class Doc {
	/**
	 * constructor, which takes the url, title, and the text of a document
	 * 
	 * @param url
	 *            the address of the document
	 * @param title
	 * 			  the title of the document
	 * @param text
	 *            the text belonging to the document
	 */
	public Doc(String url, String title, String text) {
		m_url = url;
		m_title = title;
		m_text = text;
	}

	/**
	 * getter for the url
	 * 
	 * @return the url of this document
	 */
	public String getUrl() {
		return m_url;
	}

	/**
	 * getter for the title
	 * 
	 * @return the title of this document
	 */
	public String getTitle() {
		return m_title;
	}

	/**
	 * getter for the text
	 * 
	 * @return the text of this document
	 */
	public String getText() {
		return m_text;
	}

	/**
	 * a conversion method to generate a org.apache.lucene.document.Document out
	 * of the instance
	 * 
	 * @return an org.apache.lucene.document.Document with the same information
	 */
	public Document toDocument() {
		Document d = new Document();

		Field url = new TextField("url", m_url, Field.Store.YES);
		d.add(url);
		
		Field title = new TextField("title", m_title, Field.Store.YES);
		d.add(title);

		Field text = new TextField("text", m_text, Field.Store.YES);
		d.add(text);

		return d;
	}

	/**
	 * the url of this document
	 */
	private String m_url;
	/**
	 * the title of this document
	 */
	private String m_title;
	/**
	 * the text belonging to this document
	 */
	private String m_text;
}
