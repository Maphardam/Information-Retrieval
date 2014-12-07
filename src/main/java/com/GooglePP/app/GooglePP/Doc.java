package com.GooglePP.app.GooglePP;

import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;

public class Doc {
	public Doc(int id, Date date, String title, String text) {
		m_id = id;
		m_date = date;
		m_title = title;
		m_text = text;
	}
	
	public int getId() {
		return m_id;
	}
	public Date getDate() {
		return m_date;
	}
	public String getTitle() {
		return m_title;
	}
	public String getText() {
		return m_text;
	}
	
	public Document toDocument(){
		Document d = new Document();
		
		Field id = new IntField("id", m_id, Field.Store.YES);
		d.add(id);
		
		Field date = new StringField("date", DateTools.dateToString(m_date, DateTools.Resolution.MILLISECOND), Field.Store.NO);
		d.add(date);
		
		Field title = new StringField("title", m_title, Field.Store.NO);
		d.add(title);
		
		Field text = new StringField("text", m_text, Field.Store.NO);
		d.add(text);
		
		return d;
	}

	private int m_id;
	private Date m_date;
	private String m_title;
	private String m_text;
}
