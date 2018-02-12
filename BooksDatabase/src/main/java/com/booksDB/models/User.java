package com.booksDB.models;

public class User {
	private int id;
	private String nickName;
	private String password;

	public User(int id, String nickName, String pasword) {
		this(nickName, pasword);
		this.id = id;

	}

	public User(String nickName, String pasword) {
		this.nickName = nickName;
		this.password = pasword;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	public String getNickName() {
		// TODO Auto-generated method stub
		return nickName;
	}

}
