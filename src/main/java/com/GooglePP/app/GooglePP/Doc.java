package com.GooglePP.app.GooglePP;

import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;

/**
 *	a class to store information about a document
 *
 */
public class Doc {
	/**
	 * constructor, which takes the id, date, title, and the text of a document
	 * 
	 * @param id an unique id, which is assigned to the document
	 * @param date the date this document got published
	 * @param title the title of the document
	 * @param text the text belonging to the document
	 */
	public Doc(int id, Date date, String title, String text) {
		m_id = id;
		m_date = date;
		m_title = title;
		m_text = text;
	}
	
	/**
	 * getter for the id
	 * @return the unique id of this document
	 */
	public int getId() {
		return m_id;
	}
	/**
	 * getter for the date
	 * @return the date this document got published
	 */
	public Date getDate() {
		return m_date;
	}
	/**
	 * getter for the title
	 * @return the title of this document
	 */
	public String getTitle() {
		return m_title;
	}
	/**
	 * getter for the text
	 * @return the text of this document
	 */
	public String getText() {
		return m_text;
	}
	/**
	 * a conversion method to generate a org.apache.lucene.document.Document out of the instance
	 * @return an org.apache.lucene.document.Document with the same information 
	 */
	public Document toDocument(){
		Document d = new Document();
		
		Field id = new IntField("id", m_id, Field.Store.YES);
		d.add(id);
		
		Field date = new IntField("date", Integer.parseInt(DateTools.dateToString(m_date, DateTools.Resolution.DAY)), Field.Store.YES);
		d.add(date);
		
		Field title = new TextField("title", m_title, Field.Store.YES);
		d.add(title);
		
		Field text = new TextField("text", m_text, Field.Store.YES);
		d.add(text);
		
		return d;
	}
	
	/**
	 * the unique id of this document
	 */
	private int m_id;
	/**
	 * the date this document got published
	 */
	private Date m_date;
	/**
	 * the title of this document
	 */
	private String m_title;
	/**
	 * the text belonging to this document
	 */
	private String m_text;
}
