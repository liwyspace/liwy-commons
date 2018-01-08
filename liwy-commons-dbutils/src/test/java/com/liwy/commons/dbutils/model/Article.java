package com.liwy.commons.dbutils.model;

import java.sql.Date;

public class Article {
	private int id;
	private String title;
	private String author;
	private String content;
	private Date time;
	private String liwyText;
	
	public String getLiwyText() {
		return liwyText;
	}
	public void setLiwyText(String liwyText) {
		this.liwyText = liwyText;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	public Article() {
		super();
	}
	public Article(int id, String title, String author, String content, Date time) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.content = content;
		this.time = time;
	}
	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", author=" + author
				+ ", content=" + content + ", time=" + time + ", liwyText="
				+ liwyText + "]";
	}
}
