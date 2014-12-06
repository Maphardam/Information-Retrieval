package com.GooglePP.app.GooglePP;

import java.util.Date;

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

	private int m_id;
	private Date m_date;
	private String m_title;
	private String m_text;
}
