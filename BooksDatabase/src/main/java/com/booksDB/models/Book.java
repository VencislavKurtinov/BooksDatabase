package com.booksDB.models;

import java.util.Date;

public class Book {
	private int id;

	private String ganre;
	private int pages;
	private Date date;
	private String path;
	private String name;

	public Book(String ganre, String path, String name) {
		this.ganre = ganre;
		this.path = path;
		this.name = name;
	}

	public Book(int id, String ganre, int pages, Date date, String name) {
		super();
		this.id = id;
		this.ganre = ganre;
		this.pages = pages;
		this.date = date;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGanre() {
		return ganre;
	}

	public void setGanre(String ganre) {
		this.ganre = ganre;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
